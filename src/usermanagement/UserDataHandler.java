package usermanagement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserDataHandler
 */
@WebServlet("/UserDataHandler")
public class UserDataHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDataHandler() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/management.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean updateFlag = false;
		UserDataAccess uda = new UserDataAccess();

		// Get New User Information from Request
		UserInfo user = getNewUserInfo(request);

		try {
			// Update User Information
			updateFlag = uda.updateUserInfo(user);

			/// Set Data
			setData(request, updateFlag, user);
		}
		catch (Exception e) {
			updateFlag = false;
			e.printStackTrace();
		}
		doGet(request, response);
	}

	/**
	 *  Get New User Information
	 *
	 * @param request
	 * @return
	 */
	private UserInfo getNewUserInfo(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String memo = request.getParameter("memo");

		UserInfo newInfo = new UserInfo();
		newInfo.setId(id);
		newInfo.setMemo(memo);
		newInfo.setName(name);
		newInfo.setPassword(password);
		newInfo.setPhoneNumber(phoneNumber);

		return newInfo;
	}

	/**
	 * Set Data
	 *
	 * @param request
	 * @param updateFlag
	 * @param user
	 */
	private void  setData(HttpServletRequest request, boolean updateFlag, UserInfo user) {
		HttpSession session = null;

		// Get Session
		if (checkSession(request) == true) {
			// Session Already Exists
			session = request.getSession(false);
		}
		else {
			// Create New Session
			session = request.getSession(true);
		}

		// Add Message
		addMessage(session, updateFlag, user);
	}

	/**
	 * Check Session
	 *
	 * @param req
	 * @return
	 */
	private boolean checkSession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			return true;
	    }
		else {
			return false;
	    }
	}

	/**
	 * Add Message
	 *
	 * @param session
	 * @param updateFlag
	 * @param user
	 */
	private void addMessage(HttpSession session, boolean updateFlag, UserInfo user) {
		UserManagementMessage umm = new UserManagementMessage();

		// Check Update Data
		if(updateFlag == true) {
		    umm.setMessage("Updated.");
		    session.setAttribute("message", umm);
		    session.setAttribute("user", user);
		}
		else {
		    umm.setMessage("NOT Updated.");
		    session.setAttribute("message", umm);
		}
	}


}
