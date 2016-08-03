package service;

import java.util.List;

public interface IJavServie<T> {
	public List<T> getAll(String url);

	public List<T> getByPage(String url, int page);

}
