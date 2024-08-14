import flask

app = flask.Flask(__name__)  # pylint: disable=invalid-name

app.config.from_object('recipe_index.config')

import recipe_index.api  # noqa: E402  pylint: disable=wrong-import-position
