from main import app

try:
    import main.app
except:
    pass
app.run('0.0.0.0', 8976, debug=True, threaded=True)