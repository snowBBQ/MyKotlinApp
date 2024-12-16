import os
import subprocess
import threading
import socket

if '/usr/bin' not in os.environ['PATH']:
    os.environ['PATH'] = '/usr/bin:' + os.environ['PATH']

HOST = '192.168.2.35'
PORT = 65432

ALLOWED_COMMANDS = {
    "open_terminal": ['open', '-na', 'Terminal'],
    "close_terminal": ['osascript', '-e', 'tell application "Terminal" to quit']
}


def execute_command(command):
    if command in ALLOWED_COMMANDS:
        cmd = ALLOWED_COMMANDS[command]
        try:
            output = subprocess.check_output(cmd, stderr=subprocess.STDOUT)
            return output.decode('utf-8') or "Command executed successfully \n"
        except subprocess.CalledProcessError as e:
            return f"Error executing command: {e}\nOutput: {e.output.decode('utf-8')} \n"
        except Exception as e:
            return f"Exception occurred: {str(e)} \n"
    else:
        return "Command not allowed \n"


def handle_client(conn, addr):
    with conn:
        print(f"Connected by {addr}")
        while True:
            data = conn.recv(1024).decode('utf-8').strip()
            if not data:
                break
            print(f"Received command: {data}")
            result = execute_command(data)
            conn.sendall(result.encode('utf-8'))


def start_server():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        print(f"Server listening on {HOST}:{PORT}")
        while True:
            conn, addr = s.accept()
            thread = threading.Thread(target=handle_client, args=(conn, addr))
            thread.start()


if __name__ == "__main__":
    start_server()
