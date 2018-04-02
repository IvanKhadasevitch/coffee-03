package handlers;

import command.enums.CommandType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static command.enums.CommandType.ORDERS;

public class RequestHandler {
    /**
     * From the req determines the value of the parameter "command". This value indicates
     * the corresponding CommandType object. CommandType determines page Path,
     * page Name and Controller to prepare a HttpServletResponse to a HttpServletRequest
     *
     * @param req incoming HttpServletRequest
     * @return CommandType object
     */
    public static CommandType getCommand(HttpServletRequest req){

        String param = req.getParameter("command");
        // if no "command" came always refer to the Main page ("Orders")
        if (param == null || "".equals(param)) {
            param = "orders";
        }

        // del after debug
        System.out.println("Start RequestHandler.getCommand command= " + param);

        CommandType type = CommandType.getByPageName(param);
        req.setAttribute("title", type.getI18nKey());
        HttpSession session = req.getSession();
        String pageName = (String)session.getAttribute("pageName");
        if (pageName != null) {
            session.setAttribute("prevPage", pageName);
            session.setAttribute("pageName", type.getPageName());
            session.setAttribute("pagePath", type.getPagePath());
        } else {
            session.setAttribute("prevPage", type.getPageName());
            session.setAttribute("pageName", ORDERS.getPageName());
            session.setAttribute("pagePath", ORDERS.getPagePath());
        }

        return type;
    }
}
