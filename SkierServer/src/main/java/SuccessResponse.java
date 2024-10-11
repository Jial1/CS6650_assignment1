public class SuccessResponse {
  private String message;
  private LiftRideEvent data;

  public SuccessResponse(String message, LiftRideEvent data) {
    this.message = message;
    this.data = data;
  }

  // Getters for Gson serialization
  public String getMessage() {
    return message;
  }

  public LiftRideEvent getData() {
    return data;
  }
}
