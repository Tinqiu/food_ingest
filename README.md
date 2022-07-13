# food_ingest

Small demo app that reads from a resource file and sends the data as BrandedFood objects to a RabbitMQ exchange called "branded-food-exchange". RabbitMQ needs to be running before this app otherwise the connection will fail and the app has no mechanism for retrying .

By default the app will be looking for a resource file called branded_foods.csv which is not found in this repo. You can find the zipped file here: https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_branded_food_csv_2022-04-28.zip
