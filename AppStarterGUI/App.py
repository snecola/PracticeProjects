import tkinter as tk
from tkinter import filedialog, Text
import os

root = tk.Tk()
apps = []


if os.path.isfile('save.txt'):
    with open('save.txt','r') as f:
        tempApps = f.read()
        tempApps = tempApps.split(',')
        apps = [x for x in tempApps if x.strip()]


def addApp():

    for widget in frame.winfo_children():
        widget.destroy()

    filename = filedialog.askopenfilename(initialdir="/", title="Select File", filetypes=(("executables","*.exe"), ("All Files", "*.*")))
    apps.append(filename)
    print(filename)

    for app in apps:
        label = tk.Label(frame, text=app, bg="gray")
        label.pack()


def runApps():
    for app in apps:
        os.startfile(app)

def resetAll():
    #Clear the List of Apps
    apps.clear()
    print(apps)
    #Clear the labels in the frame
    for widget in frame.winfo_children():
        widget.destroy()
    #Reset the text file
    with open('save.txt','w') as f:
        f.close()
    

canvas = tk.Canvas(root, height=700, width=700, bg="navy")
canvas.pack(fill=tk.Y)

frame = tk.Frame(root, bg = "White")
frame.place(relwidth=0.8, relheight=0.9, relx=0.1, rely=0.1)

addProgram = tk.Button(root, text="Add Program", padx=10, pady=5, fg="white", bg="black", command=addApp)
addProgram.pack()

runPrograms = tk.Button(root, text="Run Programs", padx=10, pady=5, fg="white", bg="black", command=runApps)
runPrograms.pack()

resetApp = tk.Button(root, text="Reset All", padx= 10, pady=5, fg="pink", bg="maroon", command=resetAll)
resetApp.pack()

for app in apps:
    label = tk.Label(frame, text=app, bg="gray")
    label.pack()

root.mainloop()

with open('save.txt', 'w') as f:
    for app in apps:
        f.write(app + ',')