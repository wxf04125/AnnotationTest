@DBTable(name = "MEMBER")
public class Member {
	
	// ��ʹ��ע������У������Ԫ����value������ֻ��value��Ҫ��ֵ��
	// ��ֻ��Ҫ��()�н�ֵд��
	@SQLString(30)
	private String firstName;
	
	@SQLString(50)
	private String lastName;
	
	@SQLInteger
	private Integer age;
	
	@SQLString(value = 30, name = "handle", constraints = @Constraints(primaryKey = true) )
	private String handle;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}
}