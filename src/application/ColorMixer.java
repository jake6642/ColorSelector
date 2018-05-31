package application;
/*
 * Jacob Collins
 * Human Computer Interaction 
 * 2018-02-09
 * 
 * Purpose:
 * This program will allow the user to change the 
 * color of the object to any range of the RGB 
 * spectrum. By sliding the Red, Green, and Blue
 * sliders, the sphere will adapt to match the 
 * color. 
 * 
 * To get the user started, there are a few presets
 * afforded at the bottom of the window that will
 * automatically place the sliders and adjust the rgb
 * value of the sphere. 
 * 
 * The opacity can also be adjusted for the sphere. 
 *  
 * */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ColorMixer extends Application {

	// values
	private int redVal;
	private int greenVal;
	private int blueVal;
	private double opacVal;
	final double DEFAULT = 127.5;
	final int MAX = 255;
	// sliders
	private Slider redSlide;
	private Slider greenSlide;
	private Slider blueSlide;
	private Slider opacitySlide;
	// boxes
	VBox mainBox;
	VBox topBox;
	VBox centerBox;
	VBox bottomBox;
	FlowPane buttonFlow;
	// buttons
	Button yellowBtn;
	Button orangeBtn;
	Button pinkBtn;
	Button aquaBtn;
	Button greyBtn;
	// Labels
	final String GREY = "Grey";
	final String YELLOW = "Yellow";
	final String ORANGE = "Orange";
	final String AQUA = "Aqua";
	final String PINK = "Pink";
	String TOOLTIP = "";
	Label values;
	Tooltip tp;
	// Random Objects
	Sphere circle;
	PhongMaterial material;
	Scene scene;

	@Override
	public void start(Stage stage) {

		/* Initialize everything */
		initNodes();
		initSliders();
		initPresets();
		initListeners();
		buildBoxes();
		resetColor(null);

		/* Set the Stage */
		stage.setScene(scene);
		stage.setMinHeight(scene.getHeight() + 10);
		stage.setMinWidth(scene.getWidth() + 10);
		stage.setTitle("Color Mixer");
		stage.show();
	}

	/* Create instances of each node to be used */
	private void initNodes() {
		mainBox = new VBox();
		centerBox = new VBox();
		topBox = new VBox();
		bottomBox = new VBox();
		buttonFlow = new FlowPane();
		material = new PhongMaterial();
		scene = new Scene(mainBox, 420, 680);
		circle = new Sphere(scene.getHeight() / 5);
		tp = new Tooltip();
		values = new Label(TOOLTIP);
		Tooltip.install(circle, tp);
	}

	/*
	 * This will initialize all of the sliders and their layouts while setting the
	 * default values for everything.
	 */
	private void initSliders() {

		/* Create the slides */
		redSlide = new Slider(0, MAX, DEFAULT);
		greenSlide = new Slider(0, MAX, DEFAULT);
		blueSlide = new Slider(0, MAX, DEFAULT);
		opacitySlide = new Slider(0, 1, 1);
		/* style the slides */
		redSlide.setStyle("-fx-control-inner-background: red");
		greenSlide.setStyle("-fx-control-inner-background: green");
		blueSlide.setStyle("-fx-control-inner-background: blue");
		opacitySlide.setStyle("-fx-padding: 10;" + "-fx-border-style: solid outside;" + "-fx-border-width: .25;"
				+ "-fx-border-insets: 0;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		/* This will work as a label for the rgb sliders */
		blueSlide.setMajorTickUnit(MAX / 5);
		blueSlide.setShowTickMarks(true);
		blueSlide.setShowTickLabels(true);
		/* fix up the opacity slider */
		opacitySlide.setMajorTickUnit(0.5);
		opacitySlide.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n < 0.1)
					return "0 %";
				if (n == 0.5)
					// This serves as a label for the opacity
					return "0pacity";
				return "100 %";
			}

			@Override
			public Double fromString(String s) {
				return null;
			}
		});
		opacitySlide.setShowTickLabels(true);

		/* initialize the values from all of the sliders */
		redVal = (int) redSlide.getValue();
		greenVal = (int) greenSlide.getValue();
		blueVal = (int) blueSlide.getValue();
		opacVal = opacitySlide.getValue();
	}

	/* Build and style the preset buttons */
	private void initPresets() {

		greyBtn = new Button(GREY);
		yellowBtn = new Button(YELLOW);
		orangeBtn = new Button(ORANGE);
		pinkBtn = new Button(PINK);
		aquaBtn = new Button(AQUA);

		greyBtn.setPrefWidth(90);
		yellowBtn.setPrefWidth(90);
		orangeBtn.setPrefWidth(90);
		pinkBtn.setPrefWidth(90);
		aquaBtn.setPrefWidth(90);
		greyBtn.setStyle("-fx-background-color:" + "  linear-gradient(darkgrey, grey);" + "-fx-background-radius: 30;"
				+ "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;"
				+ "-fx-font-size: 14px;" + "-fx-padding: 10 20 10 20;");
		yellowBtn.setStyle("-fx-background-color:" + "  linear-gradient(rgba(255,255,0,1), rgba(255,245,0,0.2));"
				+ "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-padding: 10 20 10 20;");
		orangeBtn.setStyle("-fx-background-color:" + "  linear-gradient(rgba(255,165,0,1), rgba(255,155,0,0.2));"
				+ "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-padding: 10 20 10 20;");
		aquaBtn.setStyle("-fx-background-color:" + "  linear-gradient(rgba(0,255,255,1), rgba(0,255,245,0.2));"
				+ "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-padding: 10 20 10 20;");
		pinkBtn.setStyle("-fx-background-color:" + "  linear-gradient(rgba(255,192,203,1), rgba(255,182,203,0.2));"
				+ "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-padding: 10 20 10 20;");
	}

	private void initListeners() {
		/* Handle window resizing so the sphere can be re-drawn correctly */
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				circle.setRadius(scene.getHeight() / 5);
			}
		});

		/* Handle all of the sliders */
		// opacity
		opacitySlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				opacVal = new_val.doubleValue();
				updateColor();
			}
		});
		// red
		redSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				redVal = new_val.intValue();
				updateColor();
			}
		});
		// green
		greenSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				greenVal = new_val.intValue();
				updateColor();
			}
		});
		// blue
		blueSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				blueVal = new_val.intValue();
				updateColor();
			}
		});

		/* Handle all of the buttons */
		// apparently this is shorthand for an action in javafx. I like it...
		greyBtn.setOnAction(this::resetColor);
		orangeBtn.setOnAction(e -> setPreset(orangeBtn));
		yellowBtn.setOnAction(e -> setPreset(yellowBtn));
		aquaBtn.setOnAction(e -> setPreset(aquaBtn));
		pinkBtn.setOnAction(e -> setPreset(pinkBtn));

	}

	/*
	 * Build the Boxes that will display everything
	 * 
	 * TopBox: contains the RGB mixer CenterBox: contains the sphere that displays
	 * the color BottomBox: contains a few presets to aid the user MainBox: Houses
	 * all of the boxes and is root of the scene
	 * 
	 */
	private void buildBoxes() {
		/* Top Box */
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(5, 50, 0, 50));
		topBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 1;"
				+ "-fx-border-insets: 20;" + "-fx-border-radius: 5;" + "-fx-border-color: gray;");
		Label mixer = new Label("RGB Mixer Panel");
		mixer.setStyle("-fx-text-fill: linear-gradient(red, green, blue);" + "-fx-font-weight: bold;"
				+ "-fx-font-size: 24px;");
		topBox.getChildren().addAll(mixer, redSlide, greenSlide, blueSlide, opacitySlide, values);

		/* Center Box */
		centerBox.getChildren().addAll(circle);
		centerBox.setPadding(new Insets(0, 50, 0, 50));
		centerBox.setAlignment(Pos.CENTER);

		/* Bottom Box */
		bottomBox.setPadding(new Insets(0, 50, 10, 50));
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 1;"
				+ "-fx-border-insets: 20;" + "-fx-border-radius: 5;" + "-fx-border-color: gray;");
		buttonFlow.setAlignment(Pos.CENTER);
		buttonFlow.getChildren().addAll(greyBtn, yellowBtn, orangeBtn, pinkBtn, aquaBtn);
		bottomBox.getChildren().addAll(new Label("Presets"), buttonFlow);

		/* Put everything in the Main Box to be sent to the stage */
		mainBox.getChildren().addAll(topBox, centerBox, bottomBox);
	}

	/*
	 * Handle the preset button presses by updating the color sliders I know there
	 * is a better way to do this, but for the sake of time...
	 * 
	 * If the slides change, updateColor is called, therefore there is no reason to
	 * update the color after the button press
	 */
	private void setPreset(Button e) {
		switch (e.getText()) {
		case YELLOW:
			updatePreset(Color.YELLOW);
			break;
		case ORANGE:
			updatePreset(Color.ORANGE);
			break;
		case PINK:
			updatePreset(Color.PINK);
			break;
		case AQUA:
			updatePreset(Color.AQUA);
			break;
		default:
			resetColor(null);
			break;
		}
	}

	/*
	 * Update the sliders based on the preset color input javafx uses 0-1 values so
	 * we have to multiply by 255 to set the slider value
	 */
	private void updatePreset(Color color) {
		redSlide.setValue(color.getRed() * MAX);
		greenSlide.setValue(color.getGreen() * MAX);
		blueSlide.setValue(color.getBlue() * MAX);
		opacitySlide.setValue(1);
	}

	/*
	 * This will apply/re-apply the color to our sphere by repainting the material
	 * It will also update the label and tooltip that indicates our current rgb
	 * value
	 */
	private void updateColor() {
		TOOLTIP = "R: " + redVal + " G: " + greenVal + " B: " + blueVal;
		tp.setText(TOOLTIP);
		values.setText(TOOLTIP);
		material.setDiffuseColor(Color.rgb(redVal, greenVal, blueVal, opacVal));
		circle.setMaterial(material);
	}

	/*
	 * This will reset all of the sliders and values to their defaults and update
	 * the color on the sphere
	 */
	private void resetColor(ActionEvent e) {
		redSlide.setValue(DEFAULT);
		greenSlide.setValue(DEFAULT);
		blueSlide.setValue(DEFAULT);
		opacitySlide.setValue(1);
		updateColor();
	}

	/* Main Method */
	public static void main(String[] args) {
		launch(args);
	}
}
