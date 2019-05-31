package dev.ipsych0.myrinnia.audio;

import dev.ipsych0.myrinnia.worlds.data.Zone;
import org.lwjgl.openal.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioManager {

    private static String deviceName;
    private static long device;
    private static int[] attributes = {0};
    private static long context;
    private static ALCCapabilities alcCapabilities;
    private static ALCapabilities alCapabilities;
    private static List<Integer> buffers = new ArrayList<>();
    public static List<Source> musicFiles = new CopyOnWriteArrayList<>();
    public static List<Source> soundfxFiles = new CopyOnWriteArrayList<>();
    private static Map<String, Integer> soundMap = new HashMap<>();
    private static Zone zone;
    public static float musicVolume = 0.4f, sfxVolume = 0.15f;
    public static boolean soundMuted, sfxMuted;

    public static void init() {
        deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(deviceName);
        context = ALC10.alcCreateContext(device, attributes);

        ALC10.alcMakeContextCurrent(context);

        alcCapabilities = ALC.createCapabilities(device);
        alCapabilities = AL.createCapabilities(alcCapabilities);

    }

    public static void tick() {
        // Check for music that has ended to clean up
        if (!musicFiles.isEmpty()) {
            Collection<Source> deleted = new ArrayList<>();
            for (Source s : musicFiles) {
                if (s.isFadingIn())
                    fadeIn(s);
                else if (s.isFadingOut())
                    fadeOut(s);
                if (!s.isPlaying()) {
                    deleted.add(s);
                    s.delete();
                }
            }
            musicFiles.removeAll(deleted);
        }

        // Check for sound effects that have ended to clean up
        if (!soundfxFiles.isEmpty()) {
            Collection<Source> deleted = new ArrayList<>();
            for (Source s : soundfxFiles) {
                if (!s.isPlaying()) {
                    deleted.add(s);
                    s.delete();
                }
            }
            soundfxFiles.removeAll(deleted);
        }
    }

    private static void fadeIn(Source s) {
        s.setFadingTimer(s.getFadingTimer() + 1);
        if (s.getFadingTimer() > 150) {
            s.setFadeInVolume(s.getFadeInVolume() + (musicVolume / 0.4f * 0.002f));
            s.setVolume(s.getFadeInVolume());
            if (s.getFadeInVolume() >= musicVolume) {
                s.setFadingIn(false);
            }
        }
    }

    private static void fadeOut(Source s) {
        s.setFadeOutVolume(s.getFadeOutVolume() - (musicVolume / 0.4f * 0.002f));
        s.setVolume(s.getFadeOutVolume());
        if (s.getFadeOutVolume() <= 0.0f) {
            s.setFadingOut(false);
            s.delete();
        }
    }

    public static void setListenerData() {
        AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

    public static int loadSound(String file) throws FileNotFoundException {
        // If the sound has been loaded already, load the same buffer to prevent memory consumption
        if (soundMap.containsKey(file)) {
            return soundMap.get(file);
        }
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        soundMap.put(file, buffer);
        WaveData waveFile = WaveData.create(file);
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        return buffer;
    }

    public static void fadeSongs(Zone zone, int buffer) {
        if (musicFiles.size() > 0) {
            if (AudioManager.zone != null) {
                if (!AudioManager.zone.getMusicFile().equals(zone.getMusicFile())) {
                    AudioManager.zone = zone;
                    musicFiles.add(new Source());
                    if (musicFiles.size() > 2) {
                        for (int i = 1; i < musicFiles.size() - 1; i++) {
                            musicFiles.get(i).delete();
                        }
                    } else {
                        musicFiles.get(0).setFadingOut(true);
                    }
                    musicFiles.get(musicFiles.size() - 1).setVolume(0.0f);
                    musicFiles.get(musicFiles.size() - 1).setFadingIn(true);
                    musicFiles.get(musicFiles.size() - 1).setLooping(true);
                    musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
                } else {
                    AudioManager.zone = zone;
                    for (int i = 0; i < musicFiles.size() - 1; i++) {
                        musicFiles.get(i).setFadingOut(true);
                    }
                }
            }
        } else {
            AudioManager.zone = zone;
            musicFiles.add(new Source());
            musicFiles.get(musicFiles.size() - 1).setVolume(musicVolume);
            musicFiles.get(musicFiles.size() - 1).setLooping(true);
            musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
        }
    }

    public static void fadeSongs(String newSong, int buffer) {
        if (musicFiles.size() > 0) {
            if (AudioManager.zone != null) {
                if (!AudioManager.zone.getMusicFile().equals(newSong)) {
                    musicFiles.add(new Source());
                    if (musicFiles.size() > 2) {
                        for (int i = 1; i < musicFiles.size() - 1; i++) {
                            musicFiles.get(i).delete();
                        }
                    } else {
                        musicFiles.get(0).setFadingOut(true);
                    }
                    musicFiles.get(musicFiles.size() - 1).setVolume(0.0f);
                    musicFiles.get(musicFiles.size() - 1).setFadingIn(true);
                    musicFiles.get(musicFiles.size() - 1).setLooping(true);
                    musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
                } else {
                    for (int i = 0; i < musicFiles.size() - 1; i++) {
                        musicFiles.get(i).setFadingOut(true);
                    }
                }
            }
        } else {
            musicFiles.add(new Source());
            musicFiles.get(musicFiles.size() - 1).setVolume(musicVolume);
            musicFiles.get(musicFiles.size() - 1).setLooping(true);
            musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
        }
    }

}
