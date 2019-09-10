package domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

@Document(collection = "stuff")
public class Stuff {
	@Id
	private String id;
	@NotNull
	@javax.validation.constraints.Size(min=4, max=32)
	
	private String name;
	@NotNull
	@javax.validation.constraints.Size(min=1, max=128)
	
	private String description;
	@NotNull
	private Size size;
	
	public Stuff() {
		this("", "", Size.MEDIUM);
	}
	
	public Stuff(String name, String description, Size size) {
		this.name = name;
		this.description = description;
		this.size = size;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	
}
