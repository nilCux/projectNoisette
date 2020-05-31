/***********************************************************************/
/*********************** Global Parameters *****************************/
/***********************************************************************/

/** Max quantity of heart beat check failure allowed */
const missMax = 3
/** Time delay before a worker reboots */
const rebootDelay = 5000
/** RSS(Resident Set Size) memory usage limit (In byte)*/
const rssMemoryLimit = 700 * 1024 * 1024
/** Time interval between heat beat checks */
const heartBeatInterval =10000

/***********************************************************************/
/***********************************************************************/
const cluster = require('cluster')



/**********************************************************************/
function launchServiceInMultiProcesses(serviceLauncherFunction, processQuantity) {
    cluster.once('online', () => {
        console.log("----- Launching " + processQuantity + " instances.")
    }) 

    if (cluster.isMaster) {
        for (let i = 0; i < processQuantity; i++) {
            createWorker();
        };

        cluster.on('exit', (worker, code, signal) => {
            console.log(`Worker on ${worker.process.pid} died with return code : ${code} and final signal! ${signal}`);
            console.log(`This process will reboot a worker in ${rebootDelay} ms.`)
            setTimeout(() => {
                createWorker()
            }, rebootDelay)
        });
    } else {
        // Process exists when an <uncaughtException> is caught
        process.on('uncaughtException', function (err) {
            console.log(err);
            process.exit(1);
        });

        // Response heart beat check
        process.on('message', function (msg) {
            if (msg == 'ping#' + process.pid) {
                process.send('pong#' + process.pid);
            }
        });

    // Memory usage control
        if (process.memoryUsage().rss > rssMemoryLimit) {
            process.exit();
        }

        serviceLauncherFunction()
    }

    function createWorker() {
        var worker = cluster.fork();

        var missed = 0; // Ping count

        // Heart beat controller
        var timer = setInterval(function () {
            if (missed >= missMax) {
                clearInterval(timer);
                console.log(worker.process.pid + ' has become a zombie!');
                process.kill(worker.process.pid);
                return;
            }
            missed++;
            worker.send('ping#' + worker.process.pid);
        }, heartBeatInterval);
        worker.on('message', function (msg) {
            if (msg == 'pong#' + worker.process.pid) {
                missed--;
            }
        });
        worker.on('exit', function () {
            clearInterval(timer);
        });
    }
}

module.exports = launchServiceInMultiProcesses