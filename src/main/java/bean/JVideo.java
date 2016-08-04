package bean;

import java.util.List;

public class JVideo {
	// http://j8vlib.com/cn/?v=javlilrhza
	// http://j8vlib.com/cn/?v=javlilawzq
	private String id;
	private String name;
	private String date;
	private List<JTag> tags;
	private String url;
	private double score;
	private int length;
	private String image;
	private List<JComment> comments;
	private List<JStar> stars;
	private JMaker maker;
	private JLabel label;
	private JDirector director;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<JTag> getTags() {
		return tags;
	}

	public void setTags(List<JTag> tags) {
		this.tags = tags;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<JComment> getComments() {
		return comments;
	}

	public void setComments(List<JComment> comments) {
		this.comments = comments;
	}

	public List<JStar> getStars() {
		return stars;
	}

	public void setStars(List<JStar> stars) {
		this.stars = stars;
	}

	public JMaker getMaker() {
		return maker;
	}

	public void setMaker(JMaker maker) {
		this.maker = maker;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JDirector getDirector() {
		return director;
	}

	public void setDirector(JDirector director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "JVideo [id=" + id + ", name=" + name + ", date=" + date + ", tags=" + tags + ", url=" + url + ", score="
				+ score + ", length=" + length + ", comments=" + comments + ", stars=" + stars + ", maker=" + maker
				+ ", label=" + label + ", director=" + director + "]";
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
