public class Deneme {

    public static void main(String[] args) {
        Deneme deneme=new Deneme();
        int [] nums=new int[]{11,13,17,19,21};
        System.out.println(deneme.returnMissingNum(nums));
    }
    public int returnMissingNum(int[] nums){
        int donecek=0;
        for (int i = 0; i <= nums.length; i++)
            if (nums[i]+2 != nums[i+1])
             return donecek=nums[i]+2;


        return donecek;
    }
}
