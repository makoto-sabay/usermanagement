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
 * Servlet implementation class LoginHandler
 */
@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHandler() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/login.jsp");
	    rd.forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		UserInfo user = null;

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		UserDataAccess uda = new UserDataAccess();

		try {
			// Get User Information
			user = uda.getUserInfo(id, password);

			// Get Request Dispatcher
			rd = getRequestDispatcher(request, user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}

	/**
	 * Get Session
	 *
	 * @param request
	 * @return
	 */
	private HttpSession getSession(HttpServletRequest request) {
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
		return session;
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
	 * Get Request Dispatcher
	 *
	 * @param request
	 * @param user
	 * @return Request Dispatcher
	 */
	private RequestDispatcher getRequestDispatcher(HttpServletRequest request, UserInfo user) {
		RequestDispatcher rd = null;
		HttpSession session =  null;
		UserManagementMessage umm = new UserManagementMessage();

		// Get Session
		session = getSession(request);

		// Check User
		if(user != null) {
			// User exists.
		    session.setAttribute("user", user);
			umm.setMessage("");
			session.setAttribute("message", umm);
		    rd = getServletConfig().getServletContext().getRequestDispatcher("/management.jsp");
		}
		else {
			// User does not exists.
			umm.setMessage("Check your id and password.");
			session.setAttribute("message", umm);
			rd = getServletConfig().getServletContext().getRequestDispatcher("/loginerror.jsp");
		}

		return rd;
	}
}
