import java.io.*;
import java.util.ArrayList;

class Playlist {
    private static final String PLAYLIST_FOLDER_NAME = "playlists";
    private static File playlistFolder = new File(PLAYLIST_FOLDER_NAME);

    private static void verifyPlaylistFolder() {
        if(!playlistFolder.exists()) {
            playlistFolder.mkdirs();
        }
    }

    static void save(String[] paths, String playlistTitle) {
        verifyPlaylistFolder();
        final String CUSTOM_FILE_TYPE = ".nnp"; //nnp = "noise ninja playlist"
        File saveFile = new File(PLAYLIST_FOLDER_NAME + "/" + playlistTitle + CUSTOM_FILE_TYPE);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
            for(int i = 0; i < paths.length; i++) {
                if(i == paths.length-1) {
                    bufferedWriter.write(paths[i].trim().replace("\\", "/"));
                } else {
                    bufferedWriter.write(paths[i].trim().replace("\\", "/") + ",");
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<File> load(File file) throws IOException {
        //return the filepaths as read from the file
        verifyPlaylistFolder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String loadedPathsAsString = bufferedReader.readLine();
        bufferedReader.close();
        String[] splitLoadedPaths = loadedPathsAsString.split(",");

        ArrayList<File> arrayOfCheckedPaths = new ArrayList<>();
        for(String path : splitLoadedPaths) {
            File currentPath = new File(path);
            if(currentPath.exists()) {
                arrayOfCheckedPaths.add(currentPath);
            }
        }
        return arrayOfCheckedPaths;
    }
}
