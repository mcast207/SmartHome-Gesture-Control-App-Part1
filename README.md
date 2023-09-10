# SmartHome Gesture Control App
This is the first part project for an android application that records user
gestures and uploads it to a flask server.

## YouTube Link
https://youtu.be/yNJ0Mco1I88


## To run flask server
Navigate to the flaskServer folder.

```bash
cd ./flaskServer
```
Set up virtual python environment.
```python
python3 -m venv ./venv
```

Activate virtual python environment
```python
. ./venv/bin/activate
```

Install flask with pip
```python
pip3 install flask
```

Export the flask application flaskserver.py as a flask app.
```python
export FLASK_APP=flaskServer
```

Run the flask server
```python
python3 flaskServer.py
```

Flask server will run at ip address 0.0.0.0 but will resolve it to a different ip address.
 - Copy the last shown IP address and paste it on line 90 of the ThirdScreenActivity.java located at: SmartHomeGestureControlApplicationPart1MiguelCastillo/app/src/main/java/com/example/smarthomegesturecontrolapplication_part1_miguelcastillo/ThirdScreenActivity.java

## Author
Miguel Castillo ID: 1224116335
