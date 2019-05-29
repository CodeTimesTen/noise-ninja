import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage rootStage) {
        VBox rootPane = new VBox();

        final int HBOX_VERTICAL_PADDING = 4;

        HBox titlePane = new HBox();
        titlePane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        titlePane.setAlignment(Pos.CENTER);

        HBox volumeSliderPane = new HBox();
        volumeSliderPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        volumeSliderPane.setAlignment(Pos.CENTER);

        HBox progressBarPane = new HBox();
        progressBarPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        progressBarPane.setAlignment(Pos.CENTER);

        HBox controlsPane = new HBox();
        controlsPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        controlsPane.setAlignment(Pos.CENTER);

        HBox playlistSkipAndRewindPane = new HBox();
        playlistSkipAndRewindPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        playlistSkipAndRewindPane.setAlignment(Pos.CENTER);

        HBox advancedControlsPane = new HBox();
        advancedControlsPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        advancedControlsPane.setAlignment(Pos.CENTER);

        HBox playlistControlPane = new HBox();
        playlistControlPane.setPadding(new Insets(HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING, 0));
        playlistControlPane.setAlignment(Pos.CENTER);

        rootPane.getChildren().addAll(titlePane, progressBarPane, volumeSliderPane, controlsPane, playlistSkipAndRewindPane, advancedControlsPane, playlistControlPane);

        //titlePane
        final Font TITLE_FONT = new Font("Cambria", 22);

        Label title = new Label("Noise Ninja");
        title.setFont(TITLE_FONT);
        titlePane.getChildren().add(title);

        final MediaPlayer[] currentSong = {null};

        //progressBarPane
        final int PROGRESS_BAR_WIDTH = 275;
        final int PROGRESS_BAR_HEIGHT = 25;

        final Font TIME_LABELS_FONT = new Font("Cambria", 12);

        Label currentSongTime = new Label("00:00/");
        currentSongTime.setFont(TIME_LABELS_FONT);

        Label songDuration = new Label("00:00");
        songDuration.setFont(TIME_LABELS_FONT);

        final int PROGRESS_BAR_PADDING = 10;

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPadding(new Insets(0, PROGRESS_BAR_PADDING, 0, PROGRESS_BAR_PADDING));
        progressBar.setMinSize(PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        progressBar.setProgress(0);

        AnimationTimer animationTimer = new AnimationTimer() {
          @Override
          public void handle(long now) {
              String currentHour;
              String currentMinute;
              String secondTimer;

              String totalHours;
              String totalMinutes;
              String totalOverflowSeconds;
              if(currentSong[0] != null) {
                  currentHour = String.valueOf((int)currentSong[0].getCurrentTime().toHours());
                  if(currentHour.length() < 2) {
                      currentHour = "0" + currentHour;
                  }
                  currentMinute = String.valueOf((int)currentSong[0].getCurrentTime().toMinutes() % 60);
                  if(currentMinute.length() < 2) {
                      currentMinute = "0" + currentMinute;
                  }
                  secondTimer = String.valueOf((int)currentSong[0].getCurrentTime().toSeconds() % 60);
                  if(secondTimer.length() < 2) {
                      secondTimer = "0" + secondTimer;
                  }


                  totalHours = String.valueOf((int)currentSong[0].getCurrentTime().toHours());
                  if(totalHours.length() < 2) {
                      totalHours = "0" + totalHours;
                  }
                  totalMinutes = String.valueOf((int)currentSong[0].getTotalDuration().toMinutes() % 60);
                  if(totalMinutes.length() < 2) {
                      totalMinutes = "0" + totalMinutes;
                  }
                  totalOverflowSeconds = String.valueOf((int)currentSong[0].getTotalDuration().toSeconds() % 60);
                  if(totalOverflowSeconds.length() < 2) {
                      totalOverflowSeconds = "0" + totalOverflowSeconds;
                  }


                  currentSongTime.setText(currentHour + ":" + currentMinute + ":" + secondTimer + "/");
                  songDuration.setText(totalHours + ":" + totalMinutes + ":" + totalOverflowSeconds);

                  progressBar.setProgress((currentSong[0].getCurrentTime().divide(currentSong[0].getTotalDuration()).toMillis()));
              }
          }
        };
        animationTimer.start();

        progressBarPane.getChildren().addAll(currentSongTime, songDuration, progressBar);

        //controlsPane
        final int BUTTON_WIDTH = 60;
        final int BUTTON_HEIGHT = 15;

        Button backTenSecondsButton = new Button("<< 10");
        backTenSecondsButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backTenSecondsButton.setOnAction(event -> currentSong[0].seek(currentSong[0].getCurrentTime().subtract(new Duration(10000))));

        Button backFiveSecondsButton = new Button("< 5");
        backFiveSecondsButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backFiveSecondsButton.setOnAction(event -> currentSong[0].seek(currentSong[0].getCurrentTime().subtract(new Duration(5000))));

        Button playButton = new Button("Play");
        playButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        playButton.setOnAction(event -> currentSong[0].play());

        Button pauseButton = new Button("Pause");
        pauseButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        pauseButton.setOnAction(event -> currentSong[0].pause());

        Button forwardFiveSecondsButton = new Button("5 >");
        forwardFiveSecondsButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        forwardFiveSecondsButton.setOnAction(event -> currentSong[0].seek(currentSong[0].getCurrentTime().add(new Duration(5000))));

        Button forwardTenSecondsButton = new Button("10 >>");
        forwardTenSecondsButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        forwardTenSecondsButton.setOnAction(event -> currentSong[0].seek(currentSong[0].getCurrentTime().add(new Duration(10000))));

        controlsPane.getChildren().addAll(backTenSecondsButton, backFiveSecondsButton, playButton, pauseButton, forwardFiveSecondsButton, forwardTenSecondsButton);
        controlsPane.setDisable(true);

        //volume slider pane
        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(volumeSlider.getMax());
        volumeSlider.setDisable(true);
        volumeSlider.valueChangingProperty().addListener((observable, oldValue, newValue) -> currentSong[0].setVolume(volumeSlider.getValue()));

        volumeSliderPane.getChildren().addAll(volumeSlider);

        final int[] currentSongPlaylistIndex = {0};//Keeps track of the current song being played in a playlist

        //playlist forward adn back pane
        Button nextSongInPlaylistButton = new Button("Skip to Next Audio File");
        nextSongInPlaylistButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        nextSongInPlaylistButton.setOnAction(event -> currentSong[0].seek(currentSong[0].getTotalDuration().add(new Duration(2000))));

        Button previousSongInPlaylistButton = new Button("Go Back to Previous Audio File");
        previousSongInPlaylistButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        previousSongInPlaylistButton.setOnAction(event -> {
            if(currentSongPlaylistIndex[0] > 0) {
                currentSongPlaylistIndex[0]-= 2;
                currentSong[0].seek(currentSong[0].getTotalDuration());
            }
        });

        playlistSkipAndRewindPane.getChildren().addAll(previousSongInPlaylistButton, nextSongInPlaylistButton);
        playlistSkipAndRewindPane.setDisable(true);

        //advancedControlsPane
        Button openFileButton = new Button("Open Audio File");
        openFileButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        openFileButton.setOnAction(event -> {
            if (currentSong[0] != null) {
                currentSong[0].pause();
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.mp3", "*.mp4", "*.wav", "*.aif", "*.aiff"),
                    new FileChooser.ExtensionFilter("MP3 Files (*.mp3)", "*.mp3"),
                    new FileChooser.ExtensionFilter("MP4 Files (*.mp4)", "*.mp4"),
                    new FileChooser.ExtensionFilter("WAV Files, (*.wav)", "*.wav"),
                    new FileChooser.ExtensionFilter("AIFF Files (*.aif, *aiff)", "*.aif", "*.aiff")
                    );

            File selectedFile = fileChooser.showOpenDialog(rootStage);
            currentSong[0] = new MediaPlayer(new Media(selectedFile.toURI().toString()));

            title.setText(selectedFile.getName());

            controlsPane.setDisable(false);
            playlistSkipAndRewindPane.setDisable(true);
            volumeSlider.setDisable(false);
        });

        advancedControlsPane.getChildren().addAll(openFileButton);

        //Playlist control pane
        Button createPlaylistButton = new Button("Edit a Playlist");
        createPlaylistButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        createPlaylistButton.setOnAction((ActionEvent event) -> {
            final int VBOX_PADDING = 4;

            HBox buttonPane = new HBox();
            buttonPane.setAlignment(Pos.CENTER);
            buttonPane.setPadding(new Insets(VBOX_PADDING, 0 , VBOX_PADDING, 0));

            HBox playlistTitlePane = new HBox();
            playlistTitlePane.setAlignment(Pos.CENTER);
            playlistTitlePane.setPadding(new Insets(VBOX_PADDING, 0, VBOX_PADDING, 0));

            VBox dialogRootPane = new VBox();
            dialogRootPane.setAlignment(Pos.CENTER);

            Stage playlistCreatorDialog = new Stage();

            final int PLAYLIST_CREATOR_WIDTH = 400;
            final int PLAYLIST_CREATOR_HEIGHT = 250;
            Scene dialogScene = new Scene(dialogRootPane, PLAYLIST_CREATOR_WIDTH, PLAYLIST_CREATOR_HEIGHT);

            Label playlistCreatorTitle = new Label("Playlist Creator");
            playlistCreatorTitle.setFont(TITLE_FONT);
            playlistCreatorTitle.setPadding(new Insets(VBOX_PADDING, 0 , VBOX_PADDING, 0));

            Label playlistCreatorSubTitle1 = new Label("Enter in the file paths of the audio files you want on this playlist.");
            playlistCreatorSubTitle1.setAlignment(Pos.CENTER);
            playlistCreatorSubTitle1.setPadding(new Insets(VBOX_PADDING, 0 , VBOX_PADDING, 0));

            Label playlistCreatorSubTitle2 = new Label("Separate each path with a comma.");
            playlistCreatorSubTitle2.setAlignment(Pos.CENTER);
            playlistCreatorSubTitle2.setPadding(new Insets(VBOX_PADDING, 0 , VBOX_PADDING, 0));

            Label playlistTitleLabel = new Label("Playlist title ");
            playlistTitleLabel.setPadding(new Insets(0, HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING));

            TextField playlistTitle = new TextField();
            playlistTitle.setPadding(new Insets(0, HBOX_VERTICAL_PADDING, 0, HBOX_VERTICAL_PADDING));

            TextField pathsTextArea = new TextField();
            pathsTextArea.setPadding(new Insets(VBOX_PADDING, 0 , VBOX_PADDING, 0));

            playlistTitlePane.getChildren().addAll(playlistTitleLabel, playlistTitle);

            final int BUTTON_HORIZONTAL_PADDING = 5;

            Button savePlaylistButton = new Button("Save Playlist");
            savePlaylistButton.setAlignment(Pos.CENTER);
            savePlaylistButton.setPadding(new Insets(VBOX_PADDING, BUTTON_HORIZONTAL_PADDING, VBOX_PADDING, BUTTON_HORIZONTAL_PADDING));
            savePlaylistButton.setOnAction(event1 -> Playlist.save(pathsTextArea.getText().split(","), playlistTitle.getText()));


            Button loadPlaylistButton = new Button("Load Playlist");
            loadPlaylistButton.setAlignment(Pos.CENTER);
            loadPlaylistButton.setPadding(new Insets(VBOX_PADDING, BUTTON_HORIZONTAL_PADDING, VBOX_PADDING, BUTTON_HORIZONTAL_PADDING));
            loadPlaylistButton.setOnAction(event2 -> {
                FileChooser playlistChooser = new FileChooser();
                playlistChooser.setTitle("Choose Playlist");
                playlistChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Noise Ninja Playlist File (*.nnp)", "*.nnp")
                );
                File loadedPlaylist = playlistChooser.showOpenDialog(playlistCreatorDialog);

                playlistTitle.setText(loadedPlaylist.getName().split("\\.")[0]);
                try {
                    pathsTextArea.setText(Playlist.load(loadedPlaylist).toString().replace("[", "").replace("]", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            buttonPane.getChildren().addAll(savePlaylistButton, loadPlaylistButton);

            dialogRootPane.getChildren().addAll(playlistCreatorTitle, playlistTitlePane, playlistCreatorSubTitle1, playlistCreatorSubTitle2, pathsTextArea, buttonPane);

            playlistCreatorDialog.setScene(dialogScene);
            playlistCreatorDialog.setResizable(false);
            playlistCreatorDialog.show();
        });

        Button loadPlaylistButton = new Button("Load Playlist");
        loadPlaylistButton.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        loadPlaylistButton.setOnAction(event -> {
            FileChooser playlistChooser = new FileChooser();
            playlistChooser.setTitle("Choose Playlist");
            playlistChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Noise Ninja Playlist File (*.nnp)", "*.nnp")
            );
            File loadedPlaylistFile = playlistChooser.showOpenDialog(rootStage);

            ArrayList<File> playlist = new ArrayList<>();
            try {
                playlist = Playlist.load(loadedPlaylistFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Media> playlistAsMediaObjects = new ArrayList<>();
            for(File path : playlist) {
                playlistAsMediaObjects.add(new Media(path.toURI().toString()));
            }

            for(Media m : playlistAsMediaObjects) {
                System.out.println(m.toString());
            }

            playlistSkipAndRewindPane.setDisable(false);

            ArrayList<File> finalPlaylist = playlist;
            AnimationTimer advancePlaylist = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    currentSong[0].setOnEndOfMedia(() -> {
                        currentSongPlaylistIndex[0]++;
                        currentSong[0] = new MediaPlayer(playlistAsMediaObjects.get(currentSongPlaylistIndex[0]));
                        title.setText(finalPlaylist.get(currentSongPlaylistIndex[0]).getName());
                        currentSong[0].setAutoPlay(true);
                    });
                }
            };

            currentSong[0] = new MediaPlayer(playlistAsMediaObjects.get(0));
            currentSong[0].setAutoPlay(true);
            currentSong[0].setOnEndOfMedia(() -> advancePlaylist.start());

            advancePlaylist.start();
            controlsPane.setDisable(false);
            volumeSlider.setDisable(false);
        });

        playlistControlPane.getChildren().addAll(createPlaylistButton, loadPlaylistButton);

        //rootStage
        final int STAGE_WIDTH = 400;
        final int STAGE_HEIGHT = 225;

        rootStage.setTitle("Noise Ninja");
        rootStage.setScene(new Scene(rootPane, STAGE_WIDTH, STAGE_HEIGHT));
        rootStage.setResizable(false);
        rootStage.getIcons().add(new Image("res/icon.png"));
        rootStage.show();
    }

}
