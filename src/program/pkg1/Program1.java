/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program.pkg1;

import java.io.File;
import java.util.Scanner;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Program1{
    
    public void start(){
        try{
            createWindow();
            initGL();
            render();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void createWindow()throws Exception{
        Display.setFullscreen(false);
        
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
        Display.create();
    }
    
    private void initGL(){
        glClearColor(0f, 0f, 0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(0, 640, 0, 480, 1, -1);
        
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render(){
        while(!Display.isCloseRequested()){
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                
                try{
                    File file = new File("src/coordinates.txt");
                    Scanner fileScanner = new Scanner(file);
                    while(fileScanner.hasNextLine()){
                        String str = fileScanner.nextLine();
                        String[] splitString = str.split(" ");
                        char figure = splitString[0].charAt(0);
                        switch(figure){
                            case 'l': String[] point1 = splitString[1].split(",");
                                      String[] point2 = splitString[2].split(",");
                                      drawLine(Float.parseFloat(point1[0]),Float.parseFloat(point1[1]),Float.parseFloat(point2[0]),Float.parseFloat(point2[1]));
                                      break;
                            case 'c': String[] circleCenter = splitString[1].split(",");
                                      drawCircle(Float.parseFloat(circleCenter[0]),Float.parseFloat(circleCenter[1]), Float.parseFloat(splitString[2]));
                                      break;
                            case 'e': String[] elipseCenter = splitString[1].split(",");
                                      String[] elipseRadius = splitString[2].split(",");
                                      drawElipse(Float.parseFloat(elipseCenter[0]), Float.parseFloat(elipseCenter[1]),
                                              Float.parseFloat(elipseRadius[0]), Float.parseFloat(elipseRadius[1]));
                                      break;
                            default : break;          
                        }
                        
                    }
//                    drawElipse(100,100, 45,80);
//                    drawLine(10,380,380,10);
//                    drawLine(350,50,500,70);
//                    drawElipse(450,250,75,35);
//                    drawCircle(50,50,100);
                    Display.update();
                    Display.sync(60);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                
            }
        }
        Display.destroy();
    }
    public void drawCircle(float centerX, float centerY, float radius){
        glColor3f(0f, 0f, 1f);
        float x, y, theta;
        float degree = 0;
        while(degree <= 360){
            theta = (float) ((degree * Math.PI)/180);
            x = (float) Math.cos(theta)*radius + centerX;
            y = (float) Math.sin(theta)*radius + centerY;
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            degree++;
        }   
    }
    
    //This function takes the coordinates of two end points of a line and draw the line using 
    //the midpoint algorithm.
    public void drawLine(float x1, float y1, float x2, float y2){
        glColor3f(1f, 0f, 0f);
        //Declare and initialize dx, dy, d, incrementRight, incrementUpRight, x, and y
        float dx, dy, d, incrementRight, incrementUpRight, x, y, slope;
        
        //dx = x2 - x1
        dx = x2 - x1;
        //dy = y2 - y1
        dy = y2 - y1;
        slope = dy / dx;
        
        if(slope > -1 && slope <1 ){
            
            //d = 2(dy)-dx
            d = (float) (dy - 0.5 * dx);
            //incrementRight = 2*dy
            incrementRight = 2 * dy;
            //incrementUpRight = 2*(dy-dx)
            incrementUpRight = 2 * (dy - dx);
            //x = x1
            x = x1;
            //y = y1
            y = y1;
            //draw first point
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            
            
            //when slope is positive
            if(slope >0){
                //if x1 and y1 are on the left of x2 and y2
                if(dx >0 && dy >0){
                    //d = 2(dy)-dx
                    d = (float) (dy - 0.5 * dx);
                    while(x < x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y+1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                              d = d + incrementUpRight;
                        }
                        else{
                            //x+1
                            x = x + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                              d = d + incrementRight;
                        }
                    }
                }
                //if x1 and y1 are on the right of x2 and y2
                else if(dx < 0 && dy < 0){
                    //d = 0.5dx-dy
                    d = (float) (0.5*dx - dy);
                    while(x > x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x-1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = d - (dy - dx);
                        }
                        else{
                            //x+1
                            x = x - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d - dy;
                        }
                    }
                }
            }
            //for slope is negative
            else if (slope < 0){
                //if x1 and y1 are on the right of x2 and y2
                if(dx < 0 && dy >0){
                    dy = -dy;
                    //d = 2(dy)-dx
                    d = (float) (0.5*dx - dy);
                    while(x > x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x-1;
                            y = y+1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = d - (dy - dx);
                        }
                        else{
                            //x+1
                            x = x - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d - dy;
                        }
                    }
                    
                }
                //if x1 and y1 are on the left of x2 and y2
                else if (dx > 0 && dy < 0){
                    dy = -dy;
                    d = (float) (dy - 0.5 * dx);
                    while(x < x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
//                            d = 2*(dy-dx) + d;
                              d = d + dy - dx;
                        }
                        else{
                            //x+1
                            x = x + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
//                            d = d + 2 * dy;
                              d = d + dy;
                        }
                    }
                }
            }
            
            //while(x < x2)

            
        }
        else{
            //d = (0.5 * dy)-dx
            d = (float) ((0.5 * dy) -dx);
            //incrementRight = 2*dy
            incrementRight = 2 * dx;
            //incrementUpRight = 2*(dy-dx)
            incrementUpRight = 2 * (dx - dy);
            //x = x1
            x = x1;
            //y = y1
            y = y1;
            //draw first point
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            //while(x < x2)
            if(slope > 0){
                if(dy > 0 && dy > 0){
                    //d = (0.5 * dy)-dx
                    d = (float) ((0.5 * dy) -dx);
                    while(y < y2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y+1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = 2*(dx-dy) + d;
        //                    d = d + dx - dy;
                        }
                        else{
                            //x+1
                            y = y + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d + 2 * dx;
        //                    d = d + dx;
                        }
                    } 
                }
                else if (dy < 0 && dx < 0){
                    dy = -dy;
                    dx = -dx;
                    while(y > y2){
                        if(d > 0){
                            //x+1, y+1
                            x = x-1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = 2*(dx-dy) + d;
        //                    d = d + dx - dy;
                        }
                        else{
                            //x+1
                            y = y - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d + 2 * dx;
        //                    d = d + dx;
                        }
                    } 

                }
            }
            else if (slope < 0){
                if(dy > 0 && dx < 0){
                    dx = -dx;
                    while(y < y2){
                        if(d > 0){
                            //x+1, y+1
                            x = x - 1;
                            y = y + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = 2*(dx-dy) + d;
        //                    d = d + dx - dy;
                        }
                        else{
                            //x+1
                            y = y + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d + 2 * dx;
        //                    d = d + dx;
                        }
                    } 

                }
                else if (dx > 0 && dy < 0){
                    dy = -dy;
                    while(y > y2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = 2*(dx-dy) + d;
        //                    d = d + dx - dy;
                        }
                        else{
                            //x+1
                            y = y - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d + 2 * dx;
        //                    d = d + dx;
                        }
                    } 

                }
            }
            
        }
            
                
                
    }
    public void drawElipse(float centerX, float centerY, float radius1, float radius2){
        glColor3f(0f, 1f, 0f);
        float x, y, theta;
        float degree = 0;
        while(degree <= 360){
            theta = (float) ((degree * Math.PI)/180);
            x = (float) Math.cos(theta)*radius1 + centerX;
            y = (float) Math.sin(theta)*radius2 + centerY;
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            degree++;
        }
    }
    
    public static void main(String[] args){
        Program1 p = new Program1();
        p.start();
    }
}