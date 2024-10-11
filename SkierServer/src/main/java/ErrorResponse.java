public class ErrorResponse {
  private String error;

  public ErrorResponse(String error) {
    this.error = error;
  }

  // Getter for Gson serialization
  public String getError() {
    return error;
  }
}
