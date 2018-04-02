package servlet;

import command.Controller;
import command.enums.CommandType;
import context.SpringContext;
import handlers.RequestHandler;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/frontController")
public class FrontController extends HttpServlet {
    private static Logger log = Logger.getLogger(FrontController.class);

    /**
     * Accepts all incoming HttpServletRequests. Parses the request and, according to
     * the command sent in the request, transfers control to the corresponding Controller
     * for processing the parameters of the query and preparing the data for the HttpServletResponse.
     *
     * @param req incoming HttpServletRequest
     * @param resp outgoing HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        // del after debug !!!
        System.out.println("Start FrontController. request= " + req.getQueryString());

        try {
            CommandType commandType = RequestHandler.getCommand(req);
            ApplicationContext context = SpringContext.initContext();
            Controller controller = context.getBean(commandType.getController(), Controller.class);
            controller.execute(req, resp);
        } catch (Throwable e) {
            log.error(e.getMessage());
            // refer to ERROR_PAGE
            String ERROR_PAGE = "/WEB-INF/view/layouts/anyError.jspx";
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);

        }

    }
}
