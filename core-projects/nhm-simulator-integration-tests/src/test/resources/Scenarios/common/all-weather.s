(context.weather
 (weather.case
  default: #western-scotland-weather

  (when (house.region-is WesternScotland)
    (weather
     name: western-scotland-weather
     windspeed: [  3.965  3.322  4.894  5.001  4.036  4.322  5.751  4.144  4.894  5.394  5.072  2.143  ]
     insolation: [  17.000  42.000  82.000  147.000  190.000  206.000  174.000  141.000  100.000  56.000  26.000  14.000  ]
     temperature: [  1.371  2.716  5.573  8.722  9.760  14.778  16.583  14.447  13.984  10.069  4.538  -0.629  ]
     ))
  (when (house.region-is EasternScotland)
    (weather
     windspeed: [  8.915  6.063  8.915  8.202  7.132  6.954  5.884  5.349  8.381  6.597  8.559  6.241  ]
     insolation: [  18.000  46.000  88.000  136.000  168.000  192.000  177.000  144.000  103.000  59.000  28.000  14.000  ]
     temperature: [  1.694  2.072  5.379  7.833  9.219  13.414  15.743  13.950  12.756  9.522  4.471  -1.081  ]
     ))
  (when (house.region-is NorthEast)
    (weather
     windspeed: [  5.400  3.600  5.400  5.400  5.400  1.800  3.600  1.800  1.800  3.600  3.600  1.800  ]
     insolation: [  16.000  43.000  81.000  131.000  171.000  200.000  170.000  147.000  98.000  54.000  26.000  14.000  ]
     temperature: [  2.340  2.966  6.028  8.106  9.286  12.970  16.255  14.193  14.034  9.931  5.575  0.920  ]
     ))
  (when (house.region-is YorkshireAndHumber)
    (weather
     windspeed: [  3.955  3.273  4.909  4.500  4.091  3.409  4.636  4.500  4.636  4.636  5.727  3.818  ]
     insolation: [  20.000  48.000  90.000  143.000  188.000  201.000  182.000  156.000  107.000  61.000  29.000  18.000  ]
     temperature: [  1.307  2.072  5.930  8.653  9.286  13.552  16.334  14.344  13.885  9.522  4.705  -0.080  ]
     ))
  (when (house.region-is NorthWest)
    (weather
     windspeed: [  5.649  4.133  4.891  4.409  4.237  3.995  5.339  5.511  5.787  5.545  6.234  5.098  ]
     insolation: [  23.000  49.000  96.000  153.000  196.000  225.000  198.000  165.000  115.000  67.000  33.000  19.000  ]
     temperature: [  0.613  1.715  5.314  7.615  9.055  13.656  15.857  13.747  13.035  9.085  4.003  -1.581  ]
     ))
  (when (house.region-is EastMidlands)
    (weather
     windspeed: [  4.403  4.718  3.669  5.137  4.508  4.089  5.766  5.766  5.137  5.242  5.766  3.460  ]
     insolation: [  23.000  46.000  88.000  138.000  193.000  214.000  186.000  162.000  110.000  65.000  31.000  18.000  ]
     temperature: [  2.082  2.930  6.255  8.757  10.346  14.447  17.840  15.743  14.394  10.909  5.911  -0.306  ]
     ))
  (when (house.region-is WestMidlands)
    (weather
     windspeed: [  4.270  4.270  4.841  4.370  4.169  3.866  4.942  4.942  4.673  4.639  4.807  3.295  ]
     insolation: [  25.000  48.000  95.000  143.000  193.000  220.000  192.000  162.000  116.000  68.000  34.000  21.000  ]
     temperature: [  1.307  3.073  6.092  8.722  10.346  14.237  17.147  15.228  13.984  10.034  4.739  -0.887  ]
     ))
  (when (house.region-is SouthWest)
    (weather
     windspeed: [  3.875  4.134  5.425  4.392  3.359  3.875  4.650  4.134  3.100  3.875  3.617  2.842  ]
     insolation: [  30.000  59.000  109.000  162.000  208.000  230.000  212.000  177.000  130.000  78.000  42.000  26.000  ]
     temperature: [  1.834  3.991  6.039  9.024  11.102  15.573  18.073  17.394  15.398  11.876  7.057  1.974  ]
     ))
  (when (house.region-is EastOfEngland)
    (weather
     windspeed: [  3.944  4.338  5.226  4.536  3.944  3.845  5.226  5.423  5.127  4.930  5.423  3.254  ]
     insolation: [  26.000  52.000  96.000  137.000  189.000  218.000  194.000  158.000  122.000  71.000  37.000  22.000  ]
     temperature: [  0.855  2.323  6.353  8.826  10.034  14.341  17.840  15.881  14.341  10.415  5.106  -0.339  ]
     ))
  (when (house.region-is SouthEast)
    (weather
     windspeed: [  3.413  4.291  4.730  4.389  3.755  3.804  4.681  4.633  3.999  4.096  4.486  3.413  ]
     insolation: [  25.000  51.000  93.000  132.000  178.000  213.000  188.000  159.000  120.000  70.000  37.000  21.000  ]
     temperature: [  0.758  2.716  5.573  8.895  9.966  14.447  17.360  15.351  13.506  10.415  5.240  0.145  ]
     ))
  (when (house.region-is London)
    (weather
     windspeed: [  4.013  6.019  6.822  5.217  4.214  3.612  4.214  4.414  4.013  5.016  5.016  3.612  ]
     insolation: [ 25.000  50.000  93.000  134.000  180.000  212.000  190.000  160.000  120.000  69.000  36.000  21.000  ]
     temperature: [  1.435  3.359  6.222  9.590  10.980  15.653  18.591  16.177  13.741  10.696  5.575  1.243  ]
     ))
  (when (house.region-is NorthernScotland)
    (weather
     windspeed: [  3.228  2.039  4.163  3.993  2.973  3.143  4.587  2.464  3.908  3.908  3.653  1.189  ]
     insolation: [  18.000  44.000  83.000  142.000  175.000  207.000  175.000  152.000  98.000  54.000  25.000  13.000  ]
     temperature: [  1.565  1.804  5.265  6.922  8.700  12.481  13.917  12.452  11.544  7.962  3.970  -1.919  ]
     ))))
