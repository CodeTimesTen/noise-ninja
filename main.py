from tkinter import *
from tkinter import ttk
import tkinter.filedialog
import pygame

root = Tk()
pygame.mixer.init()

DEFAULT_WINDOW_WIDTH = 450
DEFAULT_WINDOW_HEIGHT = 225

WINDOW_TITLE = "Sound Player"

root.geometry(str(DEFAULT_WINDOW_WIDTH) + "x" + str(DEFAULT_WINDOW_HEIGHT))
root.resizable(False, False)
root.title(WINDOW_TITLE)

DEFAULT_SONG_TITLE = "Please Select an Audio File"

SONG_TITLE_FONT = ("Helvetica", 20, "bold")
BUTTON_FONT = ("Helvetica", 9)

Y_PADDING = 6

#Root

current_song = pygame.mixer.music.load("C:/Users/Jack/Music/Wine Dark Sea I.mp3")

song_title = Label(root, text = DEFAULT_SONG_TITLE, font = SONG_TITLE_FONT)
song_title.pack(pady = Y_PADDING)

song_progress_bar = ttk.Progressbar(root, orient = "horizontal", length = 300, mode = "determinate")
song_progress_bar.pack(pady = Y_PADDING)

#Controls frame

CONTROL_BUTTONS_WIDTH = 7

controls = Frame(root)
controls.pack(pady = Y_PADDING)

SLIGHT_TIME_CHANGE_CONSTANT = 3
GREAT_TIME_CHANGE_CONSTANT = 10

def rewind_greatly():
	current_song_time_in_seconds = int(pygame.mixer.music.get_pos()/1000)
	song_time_to_skip_to = current_song_time_in_seconds - GREAT_TIME_CHANGE_CONSTANT
	
	pygame.mixer.music.rewind()
	pygame.mixer.music.play(0, song_time_to_skip_to) #TODO: play only moves it forward by the start time specefied

rewind_greatly_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = "<<<", font = BUTTON_FONT, command = rewind_greatly)
rewind_greatly_button.pack(side = LEFT)

def rewind_slightly():
	current_song_time_in_seconds = int(pygame.mixer.music.get_pos()/1000)
	song_time_to_skip_to = current_song_time_in_seconds - SLIGHT_TIME_CHANGE_CONSTANT
	
	pygame.mixer.music.rewind()
	pygame.mixer.music.play(0, song_time_to_skip_to) #TODO: play only moves it forward by the start time specefied

rewind_slightly_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = "<<", font = BUTTON_FONT, command = rewind_slightly)
rewind_slightly_button.pack(side = LEFT)

def pause():
	pygame.mixer.music.pause()

pause_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = "| |", font = BUTTON_FONT, command = pause)
pause_button.pack(pady = Y_PADDING, side = LEFT)

def play():
	if pygame.mixer.music.get_busy():
		pygame.mixer.music.unpause()
	else:
		pygame.mixer.music.play()

play_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = ">", font = BUTTON_FONT, command = play)
play_button.pack(pady = Y_PADDING, side = LEFT)

def skip_slighty():
	current_song_time_in_seconds = int(pygame.mixer.music.get_pos()/1000)
	song_time_to_skip_to = current_song_time_in_seconds + SLIGHT_TIME_CHANGE_CONSTANT
	
	pygame.mixer.music.stop() #.stop must be used for skipping and .rewind needs to be used for rewinding...the docs are not correct becuase if they were, the functions would be interchangeable
	pygame.mixer.music.play(0, song_time_to_skip_to)

skip_slightly_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = ">>", font = BUTTON_FONT, command = skip_slighty)
skip_slightly_button.pack(pady = Y_PADDING, side = LEFT)

def skip_greatly():
	current_song_time_in_seconds = int(pygame.mixer.music.get_pos()/1000)
	song_time_to_skip_to = current_song_time_in_seconds + GREAT_TIME_CHANGE_CONSTANT
	
	pygame.mixer.music.stop()
	pygame.mixer.music.play(0, song_time_to_skip_to)

skip_greatly_button = Button(controls, width = CONTROL_BUTTONS_WIDTH, text = ">>>", font = BUTTON_FONT, command = skip_greatly)
skip_greatly_button.pack(pady = Y_PADDING, side = LEFT)

#file options
file_options_frame = Frame(root)
file_options_frame.pack()

SUPPORTED_FILE_TYPES = [("MP3 files", "*.mp3"), ("MP4 Files", "*mp4")]

def open_audio_file():
	selected_file = tkinter.filedialog.askopenfilename(filetypes = SUPPORTED_FILE_TYPES)
	
	if pygame.mixer.music.get_busy():
		pygame.mixer.music.stop()
	#TODO: make the song title update with the filename that has been laoded
	current_song = pygame.mixer.music.load(selected_file)

open_audio_file_button = Button(file_options_frame, text = "Open Audio File", command = open_audio_file)
open_audio_file_button.pack()

root.mainloop()