import json


def handle_exception(some_function):
    response = {}

    def inner_exec(*args, **kwargs):
        try:
            print " Ia m here"
            return some_function(*args, **kwargs)
        except Exception as e:
            print e.message, "#$#$#$"
            response['RES'] = 'FAILURE'
            return json.dumps(response)

    return inner_exec


@handle_exception
def error_fun(a, b):
    return a / b


print error_fun(1, 1)
