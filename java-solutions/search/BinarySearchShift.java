package search;

public class BinarySearchShift {
    /*
      #              # 
      #     ->       #   
     ##             ##
    ###             ## #
   ####             ####
    
    */
    // Pred: args.length > 0
    public static void main(String[] args) {
        int[] mas = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            mas[i] = Integer.parseInt(args[i]);
        }
        //int ans = binarySearch2(mas, mas[0], -1, mas.length);
        int ans = binarySearch1(mas, mas[0]);
        System.out.println((ans == mas.length ? 0 : ans));
    }
    // Post: тесты проходятся

    // Pred: for all i [0 ... mas.length - 1] mas[i] < mas[i+1] && array shifted
    public static int binarySearch1(int[] mas, int x) {
        int l = -1;
        int r = mas.length;
        // Inv: mas[l] >= x && mas[r] < x && l < r
        while (l < r - 1) {
            // Inv && l < r - 1
            int m = (l + r) / 2;
            // m := (l + r ) / 2     (l < m < r) 
            if (mas[m] >= x) {
                // Inv && mas[m] >= x
                l = m;
                // l' := m && mas[l'] >= x
            } else {
                // Inv && mas[m] < x
                r = m;
                // r' := m && mas[r'] < x
            }
            // m = (l + r) / 2 && (l = m && r = r' || l = l' && r = m, r' - l' < r - l)
        }
        // Inv && l >= r - 1 -> l = r - 1
        // mas[l] >= x > mas[l+1]
        // mas[l] >= x > mas[r]
        return r;
    }
    //Post: R - shift

    // Pred: for all i [0 ... mas.length - 1] mas[i] < mas[i+1] && array shifted
    public static int binarySearch2(int[] mas, int x, int l, int r) {
        // Inv: mas[l] >= x && mas[r] < x && l < r
        if (l >= r - 1) {
            // Inv && l >= r - 1 -> l = r - 1
            // mas[l] >= x > mas[l+1]
            // mas[l] >= x > mas[r]
            return r;
        } else {
            // Inv && l < r - 1
            int m = (l + r) / 2;
            // m := (l + r ) / 2     (l < m < r) 
            if (mas[m] >= x) {
                // Inv && mas[m] >= x
                return binarySearch2(mas, x, m, r);
                // l' = m && mas[l'] >= x && r' - l' < r - l
            } else {
                // Inv && mas[m] < x
                return binarySearch2(mas, x, l, m);
                // r' = m && mas[r'] < x && r' - l' < r - l
            }
        }
    }
    //Post: R - shift
}
