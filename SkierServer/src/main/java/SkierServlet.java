
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

// Define the servlet with a capital 'S' to follow Java naming conventions
@WebServlet(value = "/skiers/*")
public class SkierServlet extends HttpServlet {


  private static final Gson gson = new Gson();
//  private static final Logger logger = Logger.getLogger(SkierServlet.class.getName());

  // Define constants for validation
  private static final int MIN_LIFT_ID = 1;
  private static final int MAX_LIFT_ID = 40;
  private static final int MIN_SKIER_ID = 1;
  private static final int MAX_SKIER_ID = 1_000_000;
  private static final int MIN_RESORT_ID = 1;
  private static final int MAX_RESORT_ID = 10;
  private static final int MIN_TIMESTAMP = 1;
  private static final int MAX_TIMESTAMP = 360;
  private static final int VALID_SEASON_ID = 2024;
  private static final int VALID_DAY_ID = 1;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    // Set response headers
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");


    String uri = req.getRequestURI();


    String[] urlParts = uri.split("/");
    if (!isUrlValid(urlParts)) {
      sendErrorResponse(res, HttpServletResponse.SC_NOT_FOUND, "Invalid URL format");
      return;
    }


    String requestData = readRequestBody(req);
    System.out.println(requestData);

    if (requestData.isEmpty()) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
      return;
    }


    LiftRideEvent liftRideEvent;
    try {
      liftRideEvent = gson.fromJson(requestData, LiftRideEvent.class);
      if (!isInputValid(liftRideEvent, res)) {
        return;
      }
      res.setStatus(HttpServletResponse.SC_CREATED);
      SuccessResponse successResponse = new SuccessResponse("POST request processed", liftRideEvent);
      String jsonResponse = gson.toJson(successResponse);
      PrintWriter out = res.getWriter();
      out.write(jsonResponse);
    } catch (JsonSyntaxException e) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid request body");
    }
  }

  /**
   * Reads the entire request body and returns it as a String.
   */
  private String readRequestBody(HttpServletRequest req) throws IOException {
    StringBuilder requestData = new StringBuilder();
    try (BufferedReader reader = req.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        requestData.append(line);
      }
    }
    return requestData.toString().trim();
  }

  /**
   * Sends a JSON-formatted error response with the specified status code and message.
   */
  private void sendErrorResponse(HttpServletResponse res, int statusCode, String message)
      throws IOException {
    res.setStatus(statusCode);
    ErrorResponse errorResponse = new ErrorResponse(message);
    String jsonError = gson.toJson(errorResponse);
    PrintWriter out = res.getWriter();
    out.write(jsonError);
  }

  /**
   * Validates the input LiftRideEvent object.
   */
  private boolean isInputValid(LiftRideEvent liftRideEvent, HttpServletResponse res)
      throws IOException {
    if (liftRideEvent.getLiftId() < MIN_LIFT_ID || liftRideEvent.getLiftId() > MAX_LIFT_ID) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST,
          "Lift ID should be between " + MIN_LIFT_ID + " and " + MAX_LIFT_ID);
      return false;
    }
    if (liftRideEvent.getSkierId() < MIN_SKIER_ID || liftRideEvent.getSkierId() > MAX_SKIER_ID) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST,
          "Skier ID should be between " + MIN_SKIER_ID + " and " + MAX_SKIER_ID);
      return false;
    }
    if (liftRideEvent.getResortId() < MIN_RESORT_ID
        || liftRideEvent.getResortId() > MAX_RESORT_ID) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST,
          "Resort ID should be between " + MIN_RESORT_ID + " and " + MAX_RESORT_ID);
      return false;
    }
    if (liftRideEvent.getTime() < MIN_TIMESTAMP
        || liftRideEvent.getTime() > MAX_TIMESTAMP) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST,
          "Timestamp should be between " + MIN_TIMESTAMP + " and " + MAX_TIMESTAMP);
      return false;
    }
    if (liftRideEvent.getSeasonId() != VALID_SEASON_ID
        || liftRideEvent.getDayId() != VALID_DAY_ID) {
      sendErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST,
          "Season ID must be " + VALID_SEASON_ID + " and Day ID must be " + VALID_DAY_ID);
      return false;
    }
    return true;
  }

  /**
   * Validates the URL structure /skierServer_war/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}:.
   */
  private boolean isUrlValid(String[] urlPath) {
    if (urlPath == null || urlPath.length != 10) {
      return false;
    }
    if(!urlPath[2].equals("skiers") || !urlPath[4].equals("seasons") || !urlPath[6].equals("days") || !urlPath[8].equals("skiers")) {
      return false;
    }
    try {
      Integer.parseInt(urlPath[3]);
      Integer.parseInt(urlPath[5]);
      Integer.parseInt(urlPath[7]);
      Integer.parseInt(urlPath[9]);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}