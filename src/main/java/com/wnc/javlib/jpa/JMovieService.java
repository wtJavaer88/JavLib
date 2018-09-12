package com.wnc.javlib.jpa;

import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.jpa.entity.JMovie;
import com.wnc.javlib.jpa.entity.JStar;
import com.wnc.javlib.jpa.entity.JavComment;
import com.wnc.javlib.jpa.entity.JavUser;
import com.wnc.javlib.jpa.repo.JMakeDescRepository;
import com.wnc.javlib.jpa.repo.JMovieRepository;
import com.wnc.javlib.jpa.repo.JStarRepository;
import com.wnc.javlib.jpa.repo.JTagRepository;
import com.wnc.javlib.jpa.repo.JavCommentRepository;
import com.wnc.javlib.jpa.repo.JavUserRepository;
import com.wnc.javlib.utils.JavConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;

@Service
public class JMovieService {
    @Autowired
    private JMovieRepository jMovieRepository;
    @Autowired
    private JavCommentRepository javCommentRepository;
    @Autowired
    private JavUserRepository javUserRepository;
    @Autowired
    private JMakeDescRepository jMakeDescRepository;
    @Autowired
    private JTagRepository jTagRepository;
    @Autowired
    private JStarRepository jStarRepository;

    /**
     * @Description 已存在返回true
     * @Date 9:34 2018/9/12
     * @Param [jMovie]
     * @return boolean
    */
    @Transactional
    public synchronized boolean insertMonoMovie(JMovie jMovie) {
        try {
            if(jMovieRepository.findOne(jMovie.getMovieCode()) != null){
                return false;
            }
            insertMovie(jMovie);
        } catch (Exception e) {
            e.printStackTrace();
            BasicFileUtil.writeFileString(JavConfig.APP_DIR + "jpa-mv-err.log", jMovie.getMovieCode() + ":" + e.getMessage() + "\r\n", null, true);
        }
        return true;
    }

    @Transactional
    public synchronized void insertMovie(JMovie jMovie) {
        try {
            if (!CollectionUtils.isEmpty(jMovie.getStars())) {
                for (JStar jStar : jMovie.getStars()) {
                    jStarRepository.save(jStar.cvtAlias());
                }
            }

            if (!CollectionUtils.isEmpty(jMovie.getMakeDescs())) {
                jMakeDescRepository.save(jMovie.getMakeDescs());
            }

            if (!CollectionUtils.isEmpty(jMovie.getTags())) {
                jTagRepository.save(jMovie.getTags());
            }

            if (!CollectionUtils.isEmpty(jMovie.getComments())) {
                for (JavComment javComment : jMovie.getComments()) {
                    JavUser user = javComment.getUser();
                    // user在这儿插入, 不能执行更新. 更新所需的字段需要另外处理
                    if (user != null) {
                        if (javUserRepository.findOne(user.getUid()) == null)
                            javUserRepository.save(user);
                    }
                    javComment.setMovieCode(jMovie.getMovieCode());
                    javCommentRepository.save(javComment);
                }
            }

            jMovieRepository.save(jMovie);
        } catch (Exception e) {
            e.printStackTrace();
            BasicFileUtil.writeFileString(JavConfig.APP_DIR + "jpa-mv-err.log", jMovie.getMovieCode() + ":" + e.getMessage() + "\r\n", null, true);
        }
    }

    @Transactional
    public void updateMovie(JMovie jMovie) {
        jMovieRepository.updateTorrent(jMovie.getHasTorrent(), jMovie.getMovieCode());
    }
}
