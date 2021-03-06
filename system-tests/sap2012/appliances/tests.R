## Checks whether the electricity used for SAP and BREDEM appliances matches the expected numbers.

## TODO: why is there such a high error?
EPSILON <- 30

result <- load.probe("appliance-energy")

result$occupancy <- result$occupancy..Before
result$floor.area <- result$floor.area..Before
result$sap <- result$sap..Before
result$sap.elec <- result$sap.elec..Before
result$sap.peak <- result$sap.peak..Before
result$sap.offpeak <- result$sap.offpeak..Before
result$sap.other <- result$sap - result$sap.elec
result$bredem <- result$bredem..Before

## SAP 2012 worksheet step 42
floor.threshold <- 13.9

result$sap.occupancy <- as.double(
    lapply(
        result$floor.area,
        FUN = function(floor.area) {
            if (floor.area > floor.threshold) {
                area.over.threshold <- floor.area - floor.threshold
                area.over.threshold.squared <- area.over.threshold^2

                1 +
                    (1.76 * (1 - exp(-0.000349 * area.over.threshold.squared))) +
                    (0.0013 * area.over.threshold)

            } else {
                1
            }
        }
    )
)

## SAP 2012 L10 to L12
result$sap.expected <- ((result$floor.area * result$sap.occupancy)^0.4714) * 207.8

## BREDEM 2012 1I to 1K
result$bredem.expected <- ((result$floor.area * result$occupancy)^0.4714) * 184.8

wrong.bredem <- result[abs(result$bredem - result$bredem.expected) > EPSILON,]
fail.test.if(
    nrow(wrong.bredem) > 0,
    "Appliance energy did not match the BREDEM 2012 numbers."
)

wrong.sap <- result[abs(result$sap - result$sap.expected) > EPSILON,]
fail.test.if(
    nrow(wrong.sap) > 0,
    "Appliance energy did not match the SAP 2012 numbers."
)

sap.any.other.fuel <- result[result$sap.other > 0.1,] # Allow a little floating point error here
fail.test.if(
    nrow(sap.any.other.fuel) > 0.0,
    "Appliances should only use Electricity"
)
