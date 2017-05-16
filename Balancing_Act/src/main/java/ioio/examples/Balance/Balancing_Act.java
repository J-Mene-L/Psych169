package ioio.examples.Balance;

import ioio.examples.Balance.R;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.os.Bundle;
import android.widget.TextView;


import static android.R.attr.left;
import static android.R.attr.right;

public class Balancing_Act extends IOIOActivity {

	private TextView leftnum_;
	private TextView rightnum_;
	private TextView diff_;
	private TextView position_;
	private TextView dTilt_;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		leftnum_ = (TextView) findViewById(R.id.left);
		rightnum_ = (TextView) findViewById(R.id.right);
		diff_ = (TextView) findViewById(R.id.difference);
		position_ = (TextView) findViewById(R.id.position);
		dTilt_ = (TextView) findViewById(R.id.degrees);

	}

	class Looper extends BaseIOIOLooper {
		public AnalogInput left;
		public AnalogInput right;
		private PwmOutput balanceServo_;
		private DigitalOutput led_;
		public float servoPos = 1500;
		public float lRDifference_;

		@Override
		public void setup() throws ConnectionLostException {
			led_ = ioio_.openDigitalOutput(IOIO.LED_PIN, true);
			left = ioio_.openAnalogInput(39);
			right = ioio_.openAnalogInput(37);
			balanceServo_ = ioio_.openPwmOutput(12, 100);

		}

		@Override
		public void loop() throws ConnectionLostException, InterruptedException {
			float L = left.read();
			float R = right.read();
			lRDifference_= L-R;
			servoPos=getPosition(lRDifference_,servoPos);
			balanceServo_.setPulseWidth(servoPos);
			updateUI(L, R, lRDifference_,servoPos);
			Thread.sleep(10);
		}

	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}

	private float getPosition(float diff,float pos){
		if (diff < 0){
			pos--;
			if (pos < 1000){
				pos = 1000;
			}
		}
		if (diff > 0){
			pos++;
			if (pos > 2000){
				pos = 2000;
			}
		}
		return pos;
		}


	private void updateUI(float L, float R, float diff, float pos) {
		final float left = L;
		final float right = R;
		final float d = diff;
		final float posit = pos;
		final double deg = 0.36 * (pos - 1000);
		runOnUiThread(new Runnable() {
			@Override
			 public void run() {
				 leftnum_.setText(Float.toString(left));
				 rightnum_.setText(Float.toString(right));
				 diff_.setText(Float.toString(d));
				 position_.setText(Float.toString(posit));
				 dTilt_.setText(Double.toString(deg));
			 }
		});

	}
}