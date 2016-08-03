package bean;

import java.util.List;

public class JStar {
	// http://j8vlib.com/cn/star_list.php?prefix=A
	String id;
	List<String> alias;
	String url;
	String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "JStar [id=" + id + ", alias=" + alias + ", url=" + url + ", name=" + name + "]";
	}
}
