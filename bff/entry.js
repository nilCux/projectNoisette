const os = require('os')
const cpuQuantity = os.cpus().length
const idealProcessQuantity = cpuQuantity / 2
const serviceLaunchFunction = require('./src/proxyServiceLauncher')
const launchServiceInMultiProcesses = require('./src/launchServiceInMultiProcesses')


launchServiceInMultiProcesses(serviceLaunchFunction, idealProcessQuantity)