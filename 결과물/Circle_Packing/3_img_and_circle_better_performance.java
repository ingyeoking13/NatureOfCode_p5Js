class Circle 
{
  float x;
  float y;
  float radius;
  color myColor;
  
  boolean growing = true;
  
  Circle(float x, float y, color col)
  {
    this.x=x;
    this.y=y;
    this.radius = 1;
    myColor = col;
  }
  void render()
  {
    pushMatrix();
    
    //stroke(255);
    noStroke();
    //noFill();
    fill(myColor);
    strokeWeight(1);
    ellipse(x, y, radius*2, radius*2);
    popMatrix();
  }
  
  void grow()
  {
    if (growing) radius = radius+1;
  }
  
  boolean chkEdge()
  {
    if (radius >7 ) return false; // mx radius added.
    if ( x+radius >= width || x-radius <= 0 || y+radius >= height || y-radius <=0 ) return false;
    return true;
  }
  boolean touch(Circle bc)
  {
    float dist = dist(x, y, bc.x, bc.y);
    if (dist <= bc.radius+radius) return true;
    return false;
  }
}

ArrayList<Circle> circles;

PImage img;
void setup()
{
  background(0);
  size(510,766);
  img = loadImage("C:\\Users\\yohan\\Desktop\\origin_img5.jpg");
  img.loadPixels();
  
  circles = new ArrayList<Circle>();
  
}

void draw()
{
 background(0);
  
  int stopCount=0;
  for (int i=0; i<30; i++)
  {
    Circle nc = newCircle();
    if (nc != null ) circles.add(nc);
    else 
    {
      i--;
      stopCount++;
    }
    
    if (stopCount>=100) noLoop();
  }
  for (Circle c : circles)
  {
     c.render();
     boolean tmp = c.chkEdge();
     if (!tmp) c.growing = false;
     
     for (Circle cc : circles)
     {
       if (c == cc) continue;
       if (c.touch(cc)) c.growing = false;
       
     }
     c.grow();
  }
}

Circle newCircle()
{
  boolean check_inside = false;
  
  float new_x = random(width);
  float new_y = random(height);
  for (Circle c : circles )
  { 
    float dist = dist(c.x, c.y, new_x, new_y);
    if (dist < c.radius) 
    {
      check_inside= true;
      break;
    }
  }
  if (!check_inside) return new Circle(new_x, new_y, img.pixels[int(new_x)+int(new_y)*width]);
  return null;
}