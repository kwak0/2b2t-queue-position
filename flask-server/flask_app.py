from flask import Flask, request
import queue_status

app = Flask(__name__)

@app.route("/", methods=["POST", "GET"])
def handle_queue_status():
    if request.method == "POST":
        return queue_status.queue_post()
    if request.method == "GET":
        return queue_status.queue_get()
    
if __name__ == "__main__":
    app.run()