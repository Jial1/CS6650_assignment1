import java.util.Objects;

public class LiftRideEvent {
  private int skierId;
  private int resortId;
  private int liftId;
  private int seasonId;
  private int dayId;
  private int time;

  public LiftRideEvent(int skierId, int resortId, int liftId, int seasonId, int dayId) {
    this.skierId = skierId;
    this.resortId = resortId;
    this.liftId = liftId;
    this.seasonId = seasonId;
    this.dayId = dayId;
    this.time = 0;
  }

  public int getSkierId() {
    return skierId;
  }

  public int getResortId() {
    return resortId;
  }

  public int getLiftId() {
    return liftId;
  }

  public int getSeasonId() {
    return seasonId;
  }

  public int getDayId() {
    return dayId;
  }

  public int getTime() {
    return time;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public void setSkierId(int skierId) {
    this.skierId = skierId;
  }

  public void setLiftId(int liftId) {
    this.liftId = liftId;
  }

  public void setSeasonId(int seasonId) {
    this.seasonId = seasonId;
  }

  public void setDayId(int dayId) {
    this.dayId = dayId;
  }

  public void setTime(int time) {
    this.time = time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LiftRideEvent that = (LiftRideEvent) o;
    return skierId == that.skierId && resortId == that.resortId && liftId == that.liftId
        && seasonId == that.seasonId && dayId == that.dayId && time == that.time;
  }


  @Override
  public int hashCode() {
    return Objects.hash(skierId, resortId, liftId, seasonId, dayId, time);
  }

  @Override
  public String toString() {
    return "LiftRideEvent{" +
        "skierId=" + skierId +
        ", resortId=" + resortId +
        ", liftId=" + liftId +
        ", seasonId=" + seasonId +
        ", dayId=" + dayId +
        ", time=" + time +
        '}';
  }
}
