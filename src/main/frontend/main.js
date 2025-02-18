/*
 * see: https://github.com/cuba-labs/java-electron-tutorial
 */
const {app, BrowserWindow} = require('electron');
const path = require('path');
const url = require('url');

let win;

function createWindow() {
    win = new BrowserWindow({width: 1024, height: 768});

    win.loadURL(url.format({
        pathname: path.join(__dirname, 'index.html'),
        protocol: 'file:',
        slashes: true
    }));

    win.on('closed', () => {
        win = null
    })
}

// app.on('ready', createWindow);

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
});

// app.on('activate', () => {
//     if (win === null) {
//         createWindow()
//     }
// });

platform = process.platform;

// Check operating system
if (platform === 'win32') {
    serverProcess = require('child_process')
        .spawn('cmd.exe', ['/c', 'coucal.bat'],
            {
                cwd: app.getAppPath() + '/coucal-workspaces/bin'
            });
} else {
    serverProcess = require('child_process')
        .spawn(app.getAppPath() + '/coucal-workspaces/bin/coucal');
}

let appUrl = 'http://localhost:8080';

const openWindow = function () {
    mainWindow = new BrowserWindow({
        title: 'Coucal Workspaces',
        width: 640,
        height: 480
    });

    mainWindow.loadURL(appUrl);

    mainWindow.on('closed', function () {
        mainWindow = null;
    });

    mainWindow.on('close', function (e) {
        if (serverProcess) {
            e.preventDefault();
            // kill Java executable
            const kill = require('tree-kill');
            kill(serverProcess.pid, 'SIGTERM', function () {
                console.log('Server process killed');

                serverProcess = null;

                mainWindow.close();
            });
        }
    });
};

const startUp = function () {
    const requestPromise = require('minimal-request-promise');

    requestPromise.get(appUrl).then(function (response) {
        console.log('Server started!');
        openWindow();
    }, function (response) {
        console.log('Waiting for the server start...');

        setTimeout(function () {
            startUp();
        }, 200);
    });
};

startUp();