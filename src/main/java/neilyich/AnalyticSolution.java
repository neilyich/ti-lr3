package neilyich;

public class AnalyticSolution {
    public static void solve(H h) {
        var a = h.a();
        var b = h.b();
        var c = h.c();
        var d = h.d();
        var e = h.e();

        var c2 = c*c;
        var _4ab = 4*a*b;
        var x = (-c*e/_4ab + d/(2*a)) / (c2/_4ab - 1);
        var y = (-c*d/_4ab + e/(2*b)) / (c2/_4ab - 1);
        if (x < 0) {
            System.out.println("x<0");
            x = 0;
            y = -(c*x + e) / (2*b);
        } else if (y < 0) {
            System.out.println("y<0");
            y = 0;
            x = -(c*y + d) / (2*a);
        }
        System.out.println("X = " + x);
        System.out.println("Y = " + y);
        System.out.println("H = " + h.at(x, y));
        System.out.println(h.xAt(x, y));
        System.out.println(h.yAt(x, y));
    }
}
