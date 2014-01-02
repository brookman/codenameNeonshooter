package ch.digitalmeat.poc.midishizzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TrackName;

public class MidiCompiler {
   public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
      MidiCompiler midiCompiler = new MidiCompiler();
      midiCompiler.compile(args);
      System.in.read();
   }

   public void compile(String[] args) {
      MidiFile masterFile = null;
      String fileName = "output.mid";
      Tempo tempo = null;
      MidiTrack tempTrack = null;
      List<String> toCompile = new ArrayList<String>();
      for (String arg : args) {
         if (arg.startsWith("-f:")) {
            fileName = arg.substring(3);
         } else if (arg.startsWith("-b:")) {
            try {
               tempTrack = new MidiTrack();
               float bpm = Float.parseFloat(arg.substring(3));
               TrackName tempoName = new TrackName(0, 0, "bpm:" + bpm);
               tempTrack.insertEvent(tempoName);
            } catch (Exception ex) {

            }
         } else {
            toCompile.add(arg);
         }
      }
      if (toCompile.size() == 0) {
         System.out.println("No files found...");
         return;
      }
      for (String arg : toCompile) {
         System.out.println(String.format("Loading file '%s'.", arg));
         try {
            MidiFile file = new MidiFile(new File(arg));
            System.out.println("File Resolution: " + file.getResolution());
            if (masterFile == null) {
               masterFile = file;
            } else {
               for (MidiTrack track : file.getTracks()) {
                  if (tempo != null) {
                     track.getEvents().add(tempo);
                  }
                  masterFile.addTrack(track);
               }
            }

         } catch (Exception ex) {
            System.out.println("Failed to load file.");
            System.out.println(ex.getMessage());
         }
      }
      if (tempTrack != null) {
         masterFile.addTrack(tempTrack);
      }
      System.out.println(String.format("Exporting to '%s'", fileName));
      try {
         masterFile.writeToFile(new File(fileName));
      } catch (Exception ex) {
         System.out.println("Failed to write file");
      }
   }
}
