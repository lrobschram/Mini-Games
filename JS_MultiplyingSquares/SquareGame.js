function setup() {
    var canvas = document.getElementById('myCanvas');
    var context = canvas.getContext('2d');
    var slider = document.getElementById('slider');
    slider.value = 550;
    var slider2 = document.getElementById('slider2');
    slider2.value = 550;

    let widthCanvas = slider.value;
    let heightCanvas = slider.value;
    let squareSize = 30 * slider2.value / 550;
    const limit = 200;

    function drawBorder() {
        context.strokeStyle = 'grey';
        context.beginPath();
        context.rect(0, 0, widthCanvas, heightCanvas);
        context.closePath();
        context.stroke();
    }

    function Square(x, y, xVel, yVel) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
    }


    function drawSquare(x, y, color) {
        context.fillStyle = color;
        context.strokeStyle = color;
        context.beginPath();
        context.rect(x, y, squareSize, squareSize);
        context.fill();
        context.closePath();
        context.stroke();
    }

    function moveSquare(square) {
        square.x += square.xVel;
        square.y += square.yVel;

        if (square.x > (widthCanvas - squareSize)) {
            square.xVel *= -1;
            square.x = widthCanvas - squareSize;
            if (amtOfSquares < limit) generateSquare();
        }
        else if (square.x < 0) {
            square.xVel *= -1;
            square.x = 0;
            if (amtOfSquares < limit) generateSquare();
        }


        if (square.y > (heightCanvas - squareSize)) {
            square.yVel *= -1;
            square.y = heightCanvas - squareSize;
            if (amtOfSquares < limit) generateSquare();
        }
        else if (square.y < 0) {
            square.yVel *= -1;
            square.y = 0;
            if (amtOfSquares < limit) generateSquare();
        }
    }

    let velCount = 0;
    function generateSquare() {
        let xVel = random(1, 4);
        let yVel = random(1, 4);
        if (velCount % 4 === 1) xVel *= -1;
        else if (velCount % 4 === 2) yVel *= -1;
        else if (velCount % 4 === 3) {
            xVel *= -1;
            yVel *= -1;
        }
        velCount++;

        let square = new Square(random(10, 300), random(10, 300), xVel, yVel);
        listOfSquares.push(square);
        amtOfSquares++;
    }

    function random(min, max) {
        const num = Math.floor(Math.random() * (max - min + 1)) + min;
        return num;
    }

    let listOfSquares = [];
    let firstSquare = new Square(200, 200, 2, 3);
    listOfSquares[0] = firstSquare;
    amtOfSquares = 1;

    function draw() {
        canvas.width = canvas.width;
        drawBorder();

        let colorCounter = 0;
        for (let element of listOfSquares) {
            let color = 'blue';
            if (colorCounter % 5 === 1) color = 'red';
            else if (colorCounter % 5 === 2) color = 'yellow';
            else if (colorCounter % 5 === 3) color = 'purple';
            else if (colorCounter % 5 === 4) color = 'green';
            drawSquare(element.x, element.y, color);
            moveSquare(element);
            colorCounter++;
        }

        widthCanvas = slider.value;
        heightCanvas = slider.value;
        squareSize = 30 * slider2.value / 550;
        window.requestAnimationFrame(draw);
    }

    window.requestAnimationFrame(draw);
}
window.onload = setup;
