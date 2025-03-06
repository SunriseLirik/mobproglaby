package com.example.lab1penzevright;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {
        Paint p;
        Rect rect;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 104, 255);

            // Красный прямоугольник
            p.setColor(Color.RED);
            canvas.drawRect(200, 105, 400, 200, p);

            // Красный круг
            canvas.drawCircle(100, 200, 50, p);

            // Линия
            p.setStrokeWidth(10);
            float[] points = {300, 200, 400, 200};
            canvas.drawLines(points, p);

            // Треугольник с помощью Path
            Path path = new Path();
            path.moveTo(50, 50);
            path.lineTo(0, 100);
            path.lineTo(100, 100);
            path.close(); // Автоматическое закрытие пути до начальной точки

            canvas.drawPath(path, p);

            // Желтый овал с одинаковым расстоянием от граней экрана
            p.setColor(Color.YELLOW); // Цвет овала
            int padding = 100; // Отступ от краев экрана
            RectF ovalRect = new RectF(
                    padding, // Левый край
                    padding, // Верхний край
                    getWidth() - padding, // Правый край
                    getHeight() - padding // Нижний край
            );
            canvas.drawOval(ovalRect, p); // Рисуем овал

            // Надпись по центру экрана (поверх овала)
            p.setColor(Color.BLACK); // Цвет текста
            p.setTextSize(50); // Размер текста
            String text = "10.10.1999";

            // Вычисляем ширину и высоту текста
            p.getTextBounds(text, 0, text.length(), rect);
            int x = (getWidth() - rect.width()) / 2;
            int y = (getHeight() + rect.height()) / 2;

            canvas.drawText(text, x, y, p);

            // Дополнительные фигуры с использованием графических примитивов
            // Зеленый прямоугольник
            p.setColor(Color.GREEN);
            canvas.drawRect(500, 300, 700, 500, p);

            // Синий круг
            p.setColor(Color.BLUE);
            canvas.drawCircle(600, 600, 100, p);

            // Ломаная линия (используем Path)
            Path brokenLine = new Path();
            brokenLine.moveTo(50, 400); // Начальная точка
            brokenLine.lineTo(200, 400); // Первый отрезок
            brokenLine.lineTo(200, 500); // Второй отрезок
            brokenLine.lineTo(300, 500); // Третий отрезок
            brokenLine.lineTo(300, 600); // Четвертый отрезок
            p.setColor(Color.MAGENTA); // Цвет ломаной линии
            p.setStrokeWidth(5); // Толщина линии
            p.setStyle(Paint.Style.STROKE); // Режим обводки
            canvas.drawPath(brokenLine, p);
        }
    }
}