function setup() {
    var canvas = document.getElementById('myCanvas');
    var context = canvas.getContext('2d');

    const ground = 500;
    const gravity = 0.2;

    function Circle(x, y, radius, color) {
        this.x = x;
        this.y = y;
        this.yVel = 10;
        this.xVel = -4;
        this.radius = radius;
        this.color = color;
        this.bouncesLeft = 14;
        this.drawCircle = function () {
            context.fillStyle = this.color;
            context.strokeStyle = this.color;
            context.beginPath();
            context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI);
            context.fill();
            context.closePath();
            context.stroke();
        }
    }

    function Box(x, y, size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = 'black';
        this.top = this.y;
        this.left = this.x;
        this.bottom = this.y + this.size;
        this.right = this.x + this.size;
        this.drawBox = function () {
            context.strokeStyle = this.color;
            context.lineWidth = 4;
            context.beginPath();
            context.rect(this.x, this.y, this.size, this.size);
            context.closePath();
            context.stroke();
        }
        this.resetWalls = function () {
            this.top = this.y;
            this.left = this.x;
            this.bottom = this.y + this.size;
            this.right = this.x + this.size;
        }
    }

    let circle1 = new Circle(250, 250, 10, 'blue');
    let circle2 = new Circle(250, 250, 12, 'green');
    let circle3 = new Circle(250, 250, 15, 'purple');
    let circle4 = new Circle(250, 250, 12, 'yellow');
    let circle5 = new Circle(250, 250, 10, 'teal');
    let circle6 = new Circle(250, 250, 15, 'pink');

    let listOfCircles = [];
    listOfCircles.push(circle1);
    listOfCircles.push(circle2);
    listOfCircles.push(circle3);
    listOfCircles.push(circle4);
    listOfCircles.push(circle5);
    listOfCircles.push(circle6);

    let box1 = new Box(200, 175, 100);
    let box2 = new Box(150, 125, 200);
    let box3 = new Box(100, 75, 300);
    let box4 = new Box(50, 25, 400);

    let listOfBoxes = [];
    listOfBoxes.push(box1);
    listOfBoxes.push(box2);
    listOfBoxes.push(box3);
    listOfBoxes.push(box4);

    let time = 0;
    let counter = 0;
    let ballsRemoved = [];

    function draw() {
        canvas.width = canvas.width;

        let ball = listOfCircles[0];

        for (let box of listOfBoxes) {
            box.x += Math.cos(time * Math.PI / 4);
            box.y += Math.sin(time * Math.PI / 4);
            box.drawBox();
            box.resetWalls();
        }

        ball.yVel += gravity;
        ball.y += ball.yVel

        ball.x += ball.xVel

        if (counter === 20) {
            listOfBoxes.shift();
            counter = 0;
        }
        else if (counter > 15) {
            listOfBoxes[0].color = 'red';
        }
        else if (counter > 10) {
            listOfBoxes[0].color = 'orange';
        }
        else if (counter > 5) {
            listOfBoxes[0].color = 'grey';
        }

        if (listOfBoxes.length !== 0) {

            let current = listOfBoxes[0];
            // set Y pos after bounce against box
            if (ball.y + ball.radius > current.bottom) {
                ball.y = current.bottom - ball.radius;
                ball.yVel *= -1;
                counter++;
                ball.bouncesLeft--;
            }
            if (ball.y - ball.radius < current.top) {
                ball.y = current.top + ball.radius;
                ball.yVel *= -1;
                counter++;
                ball.bouncesLeft--;
            }

            // set X pos after bounce against box
            if (ball.x + ball.radius > current.right) {
                ball.x = current.right - ball.radius;
                ball.xVel *= -1;
                counter++;
                ball.bouncesLeft--;
            }
            if (ball.x - ball.radius < current.left) {
                ball.x = current.left + ball.radius;
                ball.xVel *= -1;
                counter++;
                ball.bouncesLeft--;
            }

        }
        else {
            if (ball.y + ball.radius > ground) {
                ball.y = ground - ball.radius;
                ball.yVel *= -0.8;
            }


            if (ball.x + ball.radius > canvas.width) {
                ball.x = canvas.width - ball.radius;
                ball.xVel *= -1;
            }
            if (ball.x - ball.radius < 0) {
                ball.x = ball.radius;
                ball.xVel *= -0.9;
            }
        }

        ball.drawCircle();

        if (ball.bouncesLeft <= 0) {
            let removed = listOfCircles.shift();
            ballsRemoved.push(removed);
        }

        for (let ball of ballsRemoved) {
            ball.drawCircle();
        }

        time += 0.05;

        window.requestAnimationFrame(draw);
    }
    window.requestAnimationFrame(draw);
}
window.onload = setup;

