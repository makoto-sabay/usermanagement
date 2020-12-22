package usermanagement;

/**
 * User Information
 *
 * @author Makoto
 *
 */
public class UserInfo {
	String id = null;
	String password = null;
	String name = null;
	String phoneNumber = null;
	String memo = null;

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String num) {
		this.phoneNumber = num;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
