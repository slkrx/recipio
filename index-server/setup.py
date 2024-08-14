from setuptools import setup

setup(
    name='recipe_index',
    version='0.1.0',
    packages=['recipe_index'],
    include_package_data=True,
    install_requires=[
        'Flask',
        'requests',
    ],
    python_requires='>=3.6',
)
