package com.example.navigation;

import java.io.Serializable;

public class PostionPoint {

		private int pointX;
		private int pointY;

		public PostionPoint(int pointX, int pointY) {
			super();
			this.pointX = pointX;
			this.pointY = pointY;
		}

		public PostionPoint() {
			super();
			// TODO Auto-generated constructor stub
		}

		public int getPointX() {
			return pointX;
		}

		public void setPointX(int pointX) {
			this.pointX = pointX;
		}

		public int getPointY() {
			return pointY;
		}

		public void setPointY(int pointY) {
			this.pointY = pointY;
		}

	}


