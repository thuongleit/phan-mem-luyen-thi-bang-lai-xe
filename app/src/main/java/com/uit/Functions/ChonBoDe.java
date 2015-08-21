package com.uit.Functions;

import java.util.Random;

public class ChonBoDe {

	/**
	 * Random n questions from array count rate []..: id question, ..[]: sum
	 * number selected of this question
	 * 
	 * @param rate
	 * @param n
	 *            return an array of n question
	 */

	public int[] RandomBelongRate(int[] idQuestion, int[] countSelected, int n) {
		int[] result = new int[n];// array of result return for function,
									// content is id of questions has selected
									// random
		for (int i = 0; i < n; i++) {
			result[i] = -1; // init
		}

		// for (int i : idQuestion) {
		// Log.d("id question:", ((Integer)i).toString());
		// }
		//
		// for (int i : countSelected) {
		// Log.d("so lan xuat hien:", ((Integer)i).toString());
		// }

		int length = countSelected.length;

		// compute array rate for every question
		float[] rate = new float[length];
		rate = this.ComputeRate(countSelected);

		// for (float i : rate) {
		// Log.d("mang ti le:", ((Float)i).toString());
		// }

		// arrange array rate up
		float[] arrRateUp = new float[length];
		arrRateUp = ArrangeUp(rate);

		// for (float i : arrRateUp) {
		// Log.d("tang dan:", ((Float)i).toString());
		// }

		// arrange id question belong array rate up
		int[] idQuestionArrange = new int[length];
		idQuestionArrange = ArrangeIdQuesitonBelongrate(rate, arrRateUp,
				idQuestion);

		// for (int i : idQuestionArrange) {
		// Log.d("Cau hoi tang dan:", ((Integer)i).toString());
		// }

		/*
		 * thực thi random Nếu countSame >= n, thì random n bình thư?ng với mảng
		 * các tỉ lệ giống nhau nh? nhất Nếu countSame < n, thì random
		 * countSame, rồi sau đó tạo mảng mới vs giá trị giống nhau lớn hơn li?n
		 * k?, và sau đó random trên mảng này thêm (n- countSame) phần tử nữa
		 * Nếu tiếp tục chưa đủ (chưa finish thì lặp lại bước trên với giá trị
		 * giống nhau lớn hơn li?n k?
		 */
		int conlai = n;// số câu còn lại phải tiếp tục ch?n
		int countSum = 0;// bien dem co bao nhieu phan tu da duoc select

		int dachon = 0;// bien dem so cau da duoc chon
		boolean finish = false;
		int countSame = 0;// đếm các phần tử giống nhau
		int sumSame = 0;

		while (!finish) {
			float compare = arrRateUp[sumSame];// giá trị để so sánh

			for (int i = 0; i < length; i++) {
				if (arrRateUp[i] == compare)
					countSum++;
			}

			countSame = countSum - sumSame;
			sumSame += countSame;

			// Log.d("countSame", ((Integer)countSame).toString());
			// Log.d("SumSame", ((Integer)sumSame).toString());

			// tạo mảng id câu h?i có các tỉ lệ giống nhau
			int[] idQuestionForRandom = new int[countSame];
			for (int i = 0; i < countSame; i++) {
				// copy index
				idQuestionForRandom[i] = idQuestionArrange[countSum - countSame
						+ i]; // ok
			}

			// for (int i : idQuestionForRandom) {
			// Log.d("question for random", ((Integer)i).toString());
			// }

			// Log.d("same before", ((Integer)countSame).toString());
			if (countSame == 1) {
				result[dachon] = idQuestionForRandom[0];
				dachon++;
				conlai--;
				if (dachon == n) {
					finish = true;
					break;
				}
			} else {
				if (countSame >= conlai) {
					// random va tra ve ket qua
					int[] traveTemp = new int[conlai];
					traveTemp = RandomNFromArr(idQuestionForRandom, conlai);
					// copy qua mang result
					int j = 0;
					for (int i = dachon; i < dachon + conlai; i++) {

						result[i] = traveTemp[j];
						j++;
					}
					finish = true;
				} else {
					// random va tra ve ket qua
					int[] traveTemp = new int[countSame];
					traveTemp = RandomNFromArr(idQuestionForRandom, countSame);
					// copy qua mang result
					int j = 0;
					for (int i = dachon; i < dachon + countSame; i++) {

						result[i] = traveTemp[j];
						j++;
					}
					dachon += countSame;
					conlai -= countSame;
				}

				// Log.d("dachon", ((Integer)dachon).toString());
				// Log.d("con lai", ((Integer)conlai).toString());
				// Log.d("same after", ((Integer)countSame).toString());
			}
		}
		return result;
	}

	/**
	 * function arrange count up for an array
	 * 
	 * @param rate
	 * @return an array has arranged
	 */
	public float[] ArrangeUp(float[] rate) {
		int length = rate.length;
		float min; // element min
		float[] result = new float[length];
		for (int i = 0; i < length; i++) {
			result[i] = rate[i];
		}

		for (int i = 0; i < length - 1; i++) {
			min = result[i];
			for (int j = i + 1; j < length; j++) {
				if (result[j] < min) {
					// Metathesis
					float temp = min;
					min = result[j];
					result[i] = result[j];
					result[j] = temp;
				}
			}
		}

		return result;
	}

	/**
	 * Random between min, max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public int RamdomMinMax(int min, int max) {
		Random r = new Random();
		return Math.abs(min) + Math.abs(r.nextInt(max - min));
	}

	/**
	 * function check element don't exists in array source
	 * 
	 * @param source
	 * @param element
	 * @return
	 */
	public boolean CheckExists(int[] source, int element) {
		for (int i = 0; i < source.length; i++) {
			if (source[i] == element)
				return true;
		}
		return false;
	}

	/**
	 * function random an array n size form array source[]
	 * 
	 * @param source
	 * @param n
	 * @return
	 */
	public int[] RandomNFromArr(int[] source, int n) {
		int[] result = new int[n];
		int index = 0;

		int[] arraySelected = new int[n];// array contain selectedindex(s)

		// init array result, arrayIndex
		for (int i = 0; i < n; i++) {
			result[i] = -1;
			arraySelected[i] = -1;
		}

		int selectedIndex = this.RamdomMinMax(0, source.length); // random an
																	// index
		arraySelected[index] = selectedIndex;

		for (; index < n;) {
			while (this.CheckExists(arraySelected, selectedIndex)) {
				selectedIndex = this.RamdomMinMax(0, source.length); // random
																		// an
																		// index

			}
			arraySelected[index] = selectedIndex;
			result[index++] = source[selectedIndex];
		}

		return result;

	}

	/**
	 * function compute rate for each of question
	 * 
	 * @param countSelected
	 * @return trả v? mảng tỉ lệ tương ứng với các phần tử trong mảng nguồn
	 */
	public float[] ComputeRate(int[] countSelected) {
		int sum = 0;
		int length = countSelected.length;
		for (int i = 0; i < length; i++) {
			sum += countSelected[i];
		}

		float[] result = new float[length];
		for (int i = 0; i < length; i++) {
			result[i] = 0;
		}

		if (sum != 0) {
			for (int i = 0; i < length; i++) {
				result[i] = (float) countSelected[i] / sum;
			}
		}

		// for (float i : result) {
		// Log.d("Trong ham tinh toan ti le:", ((Float)i).toString());
		// }
		return result;
	}

	/**
	 * Hàm sắp xếp lại id câu h?i theo mảng tỉ lệ ch?n của câu h?i đó
	 * 
	 * @param before
	 *            : mảng tỉ lệ trước khi sắp xếp tương ứng với mảng idQuestion
	 * @param after
	 *            : mảng tỉ lệ tăng dần.
	 * @param idQuestion
	 * @return
	 */
	public int[] ArrangeIdQuesitonBelongrate(float[] before, float[] after,
			int[] idQuestion) {
		int length = idQuestion.length;
		int[] result = new int[length];

		int index = 0;
		// int[] daduyet = new int[length];//mang cac phan tu idQuestion da
		// duyet
		for (int i = 0; i < length; i++) {
			result[i] = -1;

		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (after[i] == before[j]
						&& !CheckExists(result, idQuestion[j])) {
					// nếu giá trị tại phần tử i của mảng đã sắp xếp
					// giống với giá trị phần tử j của mảng trước sắp xếp
					// thì lấy id của mảng kết quả là j
					result[index++] = idQuestion[j];
					break;// tăng i.
				}
			}
		}

		return result;
	}

}
