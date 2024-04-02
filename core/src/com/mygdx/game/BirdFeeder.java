public class BirdFeeder {

  private BirdFeed birdFeed;
  private float fullness;
  private final float maxFullness;
  private final long drainTime;
  private long lastUpdateTime = 0; // Stores the last time the feeder was updated


  public BirdFeeder(float maxFullness, long drainTime) {
    this.fullness = maxFullness;
    this.maxFullness = maxFullness;
    this.drainTime = drainTime; //this refers to the CURRENT instance of draintime and etc.
  }

  public boolean isEmpty() {
    return fullness <= 0;
  }

  public void refill(BirdFeed birdFeed) {
    this.birdFeed = birdFeed;
    this.fullness = maxFullness;
  }

  public void updateRealTime(long currentTime) {
    if (fullness > 0) {
      long timePassed = currentTime - lastUpdateTime;
      fullness = Math.max(0, fullness - (timePassed / (float) drainTime) * maxFullness);
      lastUpdateTime = currentTime; // Update after draining
    }
  }

  public BirdFeed getBirdFeed() {
    return birdFeed;
  }
}