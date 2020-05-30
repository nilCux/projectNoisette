const cluster = require('cluster')
const os = require ('os')

const cpuQuantity = os.cpus().length
const idealProcessQuantity = cpuQuantity / 2
let i = 0

cluster.once('online', () => {
    console.log("----- Totally, " + cpuQuantity + " CPU cores are working.")
    console.log("----- Hence, " + idealProcessQuantity + " Noisette instances will be deployed.")
}) 

if (cluster.isMaster) {
    for (; i < cpuQuantity / 2; i++) {
        cluster.fork();
    };

    cluster.on('exit', (worker, code, signal) => {
        console.log(`worker ${worker.process.pid} died`);
    });

} else {
    require('./src/app')
}
