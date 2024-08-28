package com.infinite.java.parkinglot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class RegistrationDaoImpl implements RegistrationDao {

	private String username;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> qList = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public ArrayList<String> getqList() {
		return qList;
	}

	public void setqList(ArrayList<String> qList) {
		this.qList = qList;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String err1;
	private String err2;

	public String getErr2() {
		return err2;
	}

	public void setErr2(String err2) {
		this.err2 = err2;
	}

	public String getErr1() {
		return err1;
	}

	public void setErr1(String err1) {
		this.err1 = err1;
	}

	private String err;
	private String selectedQ1;
	private String selectedQ2;
	private String selectedQ3;
	private String[] securityQuestions = { "Who is Your First Guru?", "What is Your Favourite Place?",
			"Which is your First Car?", "What is Your Favourite_Hobby?", "What is your Favourite Dish?" };
	private ArrayList<String> securityQuestionsSet2;
	private ArrayList<String> securityQuestionsSet3;
	// Map<String, Object> sesionMap =
	// FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public ArrayList<String> getSecurityQuestionsSet2() {
		return securityQuestionsSet2;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public void setSecurityQuestionsSet2(ArrayList<String> securityQuestionsSet2) {
		this.securityQuestionsSet2 = securityQuestionsSet2;
	}

	public ArrayList<String> getSecurityQuestionsSet3() {
		return securityQuestionsSet3;
	}

	public void setSecurityQuestionsSet3(ArrayList<String> securityQuestionsSet3) {
		this.securityQuestionsSet3 = securityQuestionsSet3;
	}

	public String[] getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(String[] securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	public String getSelectedQ1() {
		return selectedQ1;
	}

	public void setSelectedQ1(String selectedQ1) {
		this.selectedQ1 = selectedQ1;
	}

	public String getSelectedQ2() {
		return selectedQ2;
	}

	public void setSelectedQ2(String selectedQ2) {
		this.selectedQ2 = selectedQ2;
	}

	public String getSelectedQ3() {
		return selectedQ3;
	}

	public void setSelectedQ3(String selectedQ3) {
		this.selectedQ3 = selectedQ3;
	}

	/*
	 * public List<String> filterSecurityQuestions() { return null; }
	 */

	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void OnQ1Select(ValueChangeEvent vce) {
		this.selectedQ1 = vce.getNewValue().toString();
		ArrayList<String> securityQuestionsSet2 = new ArrayList<>(Arrays.asList(securityQuestions));
		securityQuestionsSet2.remove(selectedQ1);
		System.out.println(securityQuestionsSet2);
		this.securityQuestionsSet2 = securityQuestionsSet2;
		// sesionMap.put("securityQuestionsSet2", securityQuestionsSet2);
	}

	public void OnQ2Select(ValueChangeEvent vce) {
		this.selectedQ2 = vce.getNewValue().toString();
		ArrayList<String> securityQuestionsSet3 = new ArrayList<>(Arrays.asList(securityQuestions));
		securityQuestionsSet3.remove(selectedQ1);
		securityQuestionsSet3.remove(selectedQ2);
		System.out.println(securityQuestionsSet3);
		this.securityQuestionsSet3 = securityQuestionsSet3;
		// sesionMap.put("securityQuestionsSet3", securityQuestionsSet3);
	}

	public void OnQ3Select(ValueChangeEvent vce) {
		this.selectedQ3 = vce.getNewValue().toString();
	}

	@Override
	public String registerUser(Registration_Entity regEntity) {
		SessionFactory sf = SessionHelper.getConnection();
		Session sj = sf.openSession();
		String encr = EncryptedPassword.getCode(regEntity.getPassWord());
		// String enc =
		// EncryptedPassword.getCode(regEntity.getConfirmPassword());
		// String encr=EncryptedPassword.getCode(regEntity.getPassWord());
		regEntity.setPassWord(encr);
		// regEntity.setConfirmPassword(enc);
		Map<String, Object> sMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		/*
		 * if(isPhoneNoExits(regEntity.getPhoneNumber())){ sMap.put("err1",
		 * "Phone Number Already Exits");
		 * 
		 * return "Registration.xhtml?faces-redirect=true";
		 * 
		 * } if(isEmailExits(regEntity.getEmail())){ sMap.put("err2",
		 * "Email Already Exits");
		 * 
		 * return "Registration.xhtml?faces-redirect=true";
		 * 
		 * } if(isUsernameExits(regEntity.getUserName())){ sMap.put("err3",
		 * "User Name Already Exits");
		 * 
		 * return "Registration.xhtml?faces-redirect=true";
		 * 
		 * }
		 */

		Transaction tr = sj.beginTransaction();
		sj.save(regEntity);
		tr.commit();

		return "login.xhtml?faces-redirect=true";
	}

	public boolean isPhoneNoExits(String phoneNumber) {

		SessionFactory sf = SessionHelper.getConnection();
		Session sj = sf.openSession();
		Criteria cr = sj.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("phoneNumber", phoneNumber));
		Registration_Entity re = (Registration_Entity) cr.uniqueResult();
		if (re == null) {
			return false;

		} else {
			return true;
		}
	}

	public String authenticate(Registration_Entity l) {
		Registration_Entity l1;

		// TODO Auto-generated method stub
		Map<String, Object> sMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		// l.setPassword(encr);
		SessionFactory sf = null;
		Session s = null;
		try {
			sf = SessionHelper.getConnection();
			s = sf.openSession();
			String encr = EncryptedPassword.getCode(l.getPassWord());

			Criteria cr = s.createCriteria(Registration_Entity.class);
			cr.add(Restrictions.eq("userName", l.getUserName()));

			Registration_Entity l2 = (Registration_Entity) cr.uniqueResult();

			if (l2 != null) {

				cr.add(Restrictions.eq("passWord", encr.trim()));

				l1 = (Registration_Entity) cr.uniqueResult();

				if (l1 == null) {
					err2 = "you have entered wrong password";
					return "login.xhtml?faces-redirect=true";

				} else {

					err2 = "";

					if (l.getCategory().equals(l1.getCategory())) {

						errorMsg = "";
						sMap.put("loggedInUser", l1);
						return "HomePageNew.xhtml?faces-redirect=true";

					} else {
						errorMsg = "please select correct category....";

					}
				}

				// if(l.getPassWord().equals(l1.getPassWord())){
				//
				// errorMsg="";
				// return "HomePageNew.xhtml?faces-redirect=true";
				//
				// }else{
				// errorMsg="you have entered wrong password....";
				//
				// }
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			s.close();
			sf.close();
		}

		return "login.xhtml?faces-redirect=true";

	}

	public boolean isEmailExits(String email) {

		SessionFactory sf = SessionHelper.getConnection();
		Session sj = sf.openSession();
		Criteria cr = sj.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("email", email));
		Registration_Entity re = (Registration_Entity) cr.uniqueResult();
		if (re == null) {
			return false;

		} else {
			return true;
		}

	}

	public boolean isUsernameExits(String userName) {

		SessionFactory sf = SessionHelper.getConnection();
		Session sj = sf.openSession();
		Criteria cr = sj.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("userName", userName));
		Registration_Entity re = (Registration_Entity) cr.uniqueResult();
		if (re == null) {
			return false;

		} else {
			return true;
		}

	}

	public Registration_Entity searchByUserName(String username) {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("userName", username));
		Registration_Entity user = (Registration_Entity) cr.uniqueResult();
		System.out.println("Searched......");

		return user;

	}

	public String resetPassword(String uName, Registration_Entity entity) {

		// Registration_Entity rs=new Registration_Entity();
		// rs.setPassWord("Akash@321");

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		try {

			Criteria cr = session.createCriteria(Registration_Entity.class);
			cr.add(Restrictions.eq("userName", uName));

			Registration_Entity e = (Registration_Entity) cr.uniqueResult();

			e.setPassWord(EncryptedPassword.getCode(entity.getPassWord()));

			session.update(e);
			t.commit();
			session.close();
			sf.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			t.rollback();
			e.printStackTrace();
		}

		return "login.xhtml?faces-redirect=true";

	}

	public String getForgetPwd(String userName1) {
		qList.clear();
		System.out.println(userName1);
		Map<String, Object> sMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		Criteria cr = session.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("userName", userName1.trim()));
		Registration_Entity re = (Registration_Entity) cr.uniqueResult();

		System.out.println(re);
		if (re != null) {
			this.username = userName1;
			System.out.println(username);
			sMap.put("nameUser", username);
			System.out.println(re.getSecQ1() + "dsffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
			this.qList.add(re.getSecQ1());
			this.qList.add(re.getSecQ2());
			this.qList.add(re.getSecQ3());

			return "UserForgetPassword.xhtml?faces-redirect=true";

		}
		if (re == null) {
			err = "User Not Exists";
			return "SendOtp.xhtml?faces-redirect=true";
		}

		return null;

	}

	public String getNewPass(Registration_Entity re) {

		System.out.println(username + "USER NAME................................");
		System.out.println(re.getSecQ1());

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		Criteria cr = session.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("userName", username));

		Registration_Entity r = (Registration_Entity) cr.uniqueResult();
		System.out.println("OBJECT    " + r);

		System.out.println(r.getSecQ1());
		System.out.println(re.getSecQ1());
		// if(r.getSecQ1().equals(re.getSecQ1()) ||
		// r.getSecQ2().equals(re.getSecQ2())||
		// r.getSecQ3().equals(re.getSecQ1())){
		//
		//// if(re.getSecQ1().)
		////
		////
		//// }
		//
		// }

		if (r.getSecQ1().equals(re.getSecQ1())) {

			if (r.getAns1().equals(re.getAns1())) {

				session.update(r);
				t.commit();
				session.close();

			} else {

				err1 = "You have entered wrong Answer..";
				return "UserForgetPassword.xhtml?faces-redirect=true";
			}

		}

		if (r.getSecQ2().equals(re.getSecQ1())) {

			if (r.getAns2().equals(re.getAns1())) {

				session.update(r);
				t.commit();
				session.close();

			} else {

				err1 = "You have entered wrong Answer..";
				return "UserForgetPassword.xhtml?faces-redirect=true";
			}

		}

		if (r.getSecQ3().equals(re.getSecQ1())) {

			if (r.getAns3().equals(re.getAns1())) {

				session.update(r);
				t.commit();
				session.close();

			} else {

				err1 = "You have entered wrong Answer..";
				return "UserForgetPassword.xhtml?faces-redirect=true";
			}

		}

		return "ForgetPassword.xhtml?faces-redirect=true";
	}

	public String resetPass(Registration_Entity l) {
		Registration_Entity l1;

		// TODO Auto-generated method stub
		String encr = EncryptedPassword.getCode(l.getPassWord());
		// l.setPassword(encr);
		SessionFactory sf = SessionHelper.getConnection();
		Session s = sf.openSession();

		System.out.println(l.getUserName());
		Criteria cr = s.createCriteria(Registration_Entity.class);
		cr.add(Restrictions.eq("userName", l.getUserName()));

		l1 = (Registration_Entity) cr.uniqueResult();

		if (l1 == null) {

			errorMsg = "* You Have Entered Wrong UserName *";
			return "Login.xhtml?faces-redirect=true";

		}

		cr.add(Restrictions.eq("password", encr.trim()));

		l1 = (Registration_Entity) cr.uniqueResult();

		if (l1 != null) {

			if (encr.equals(l1.getPassWord())) {
				errorMsg = "";

			} else {
				errorMsg = "* You Have Entered Wrong Password *";
				return "Login.xhtml?faces-redirect=true";

			}
		}

		Registration_Entity login = (Registration_Entity) cr.uniqueResult();

		System.out.println(login);

		if (login == null) {
			errorMsg = "* You Have Entered Wrong Password *";

			return "Login.xhtml?faces-redirect=true";

		} else {
			if (l.getUserName().equals(login.getUserName())) {
				errorMsg = "";

				return "HomePage.xhtml?faces-redirect=true";

			} else {
				errorMsg = "* You Have entered Wrong UserName *";
				return "Login.xhtml?faces-redirect=true";

			}

		}
	}

	public String resetPasswordDao(String pwd) {

		System.out.println(pwd);

		String encr = EncryptedPassword.getCode(pwd);
		SessionFactory sf = SessionHelper.getConnection();

		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Registration_Entity.class);

		cr.add(Restrictions.eq("userName", this.username));

		Transaction t = session.beginTransaction();

		Registration_Entity a = (Registration_Entity) cr.uniqueResult();
		// String str=authenticate(a);
		a.setPassWord(encr);
		session.update(a);
		t.commit();
		qList.clear();
		return "login.xhtml?faces-redirect=true";
		// return "LoginPage.xhtml?faces-redirect=true";
	}

}
