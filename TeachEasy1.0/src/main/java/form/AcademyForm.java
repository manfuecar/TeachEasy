
package form;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class AcademyForm {

	// Attributes ----------------------------------------------------

	private int		id;

	private String	username;
	private String	password;

	private String	password2;
	private boolean	agreed;

	private String	name;
	private String	city;
	private String	address;
	private String	description;
	private String	cif;
	private String	paypalMail;
	private String	picture;


	// Constructor --------------------------------------------------

	public AcademyForm() {
		super();
	}

	// Getters and Setters ------------------------------------------

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@NotNull
	public boolean getAgreed() {
		return agreed;
	}
	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Email
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPaypalMail() {
		return paypalMail;
	}
	public void setPaypalMail(String paypalMail) {
		this.paypalMail = paypalMail;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	@Pattern(regexp="^([A B C D E F G H J N P Q R S U V W]{1}\\d{8})$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

}
