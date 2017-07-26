package frontend.parse;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Token Manager.
 * NOTE: This class is LEGACY and a product of the original JavaCC generation of this parser code.
 * As of 2017-07-26, it has been pruned of unused code and has maximal encapsulation.
 * Feel free to remove or refactor it if it no longer satisfies requirements.
 */
public class MiniJavaParserTokenManager implements MiniJavaParserConstants {
	private boolean anyErrors = false;
	private final SimpleCharStream input_stream;
	private final int[] jjrounds = new int[22];
	private final int[] jjstateSet = new int[44];
	private StringBuffer image = new StringBuffer();
	private int jjimageLen;
	private int lengthOfMatch;
	private char curChar;
	private int curLexState = 0;
	private int jjnewStateCnt;
	private int jjround;
	private int jjmatchedPos;
	private int jjmatchedKind;

	private static final long[] jjbitVec0 = {
		0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
	};

	private static final int[] jjnextStates = {
		10, 11, 13, 10, 11, 15, 13, 12, 14, 16, 
	};

	/** Token literal values. */
	private static final String[] jjstrLiteralImages = {
		"",
        null,
        null,
        null,
        null,
        null,
        "\50",
        "\51",
        "\133",
        "\135",
        "\173",
		"\175",
        "\56",
        "\55",
        "\41",
        "\52",
        "\57",
        "\53",
        "\74",
        "\76",
        "\75",
        "\75\75",
		"\41\75",
        "\46\46",
        "\174\174",
        "\54",
        "\73",
        "\160\165\142\154\151\143",
		"\163\164\141\164\151\143",
        "\166\157\151\144",
        "\155\141\151\156",
        "\123\164\162\151\156\147",
		"\143\154\141\163\163",
        "\145\170\164\145\156\144\163",
        "\124\150\162\145\141\144",
		"\163\171\156\143\150\162\157\156\151\172\145\144",
        "\162\145\164\165\162\156",
        "\151\146",
        "\145\154\163\145",
		"\167\150\151\154\145",
        "\130\151\156\165",
        "\164\162\165\145",
        "\146\141\154\163\145",
		"\164\150\151\163",
        "\156\165\154\154",
        "\156\145\167",
        "\151\156\164",
		"\142\157\157\154\145\141\156",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
		null,
        null,
	};

	/** Lex State array. */
	private static final int[] jjnewLexState = {
		-1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
		-1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        1,
        2,
		3,
        0,
        0,
        0,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
	};

	private static final long[] jjtoToken = {
		0xf80ffffffffffc1L, 
	};

	private static final long[] jjtoSkip = {
		0x403800000000003eL, 
	};

	private static final long[] jjtoSpecial = {
		0x38000000000000L, 
	};

	/** Constructor. */
	public MiniJavaParserTokenManager(SimpleCharStream stream) {
		input_stream = stream;
	}

	/** Get the next Token. */
	public Token getNextToken()	{
		Token specialToken = null;
		Token matchedToken;
		int curPos = 0;

		EOFLoop: for (;;) {
			try {
				curChar = input_stream.BeginToken();
			} catch(IOException e) {
				jjmatchedKind = 0;
				matchedToken = jjFillToken();
				matchedToken.specialToken = specialToken;
				return matchedToken;
			}
			image = new StringBuffer();
			image.setLength(0);
			jjimageLen = 0;

			for (;;) {
				switch (curLexState) {
					case 0:
						try {
							input_stream.backup(0);
							while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
								curChar = input_stream.BeginToken();
						} catch (IOException e1) {
							continue EOFLoop;
						}
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_0();
						if (jjmatchedPos == 0 && jjmatchedKind > 62) jjmatchedKind = 62;
						break;
					case 1:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_1();
						if (jjmatchedPos == 0 && jjmatchedKind > 54) jjmatchedKind = 54;
						break;
					case 2:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_2();
						if (jjmatchedPos == 0 && jjmatchedKind > 54) jjmatchedKind = 54;
						break;
					case 3:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_3();
						if (jjmatchedPos == 0 && jjmatchedKind > 54) jjmatchedKind = 54;
						break;
				}
				if (jjmatchedKind != 0x7fffffff) {
					if (jjmatchedPos + 1 < curPos) input_stream.backup(curPos - jjmatchedPos - 1);
					if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
						matchedToken = jjFillToken();
						matchedToken.specialToken = specialToken;
						if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
						return matchedToken;
					} else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
						if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
							matchedToken = jjFillToken();
							if (specialToken == null) specialToken = matchedToken;
							else {
								matchedToken.specialToken = specialToken;
								specialToken = (specialToken.next = matchedToken);
							}
							SkipLexicalActions(matchedToken);
						} else SkipLexicalActions(null);
						if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
						continue EOFLoop;
					}
					MoreLexicalActions();
					if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
					curPos = 0;
					jjmatchedKind = 0x7fffffff;
					try {
						curChar = input_stream.readChar();
						continue;
					} catch (IOException e1) { }
				}
				int error_line = input_stream.getEndLine();
				int error_column = input_stream.getEndColumn();
				String error_after = null;
				boolean EOFSeen = false;
				try {
	            	input_stream.readChar(); input_stream.backup(1);
	        	} catch (IOException e1) {
					EOFSeen = true;
					error_after = curPos <= 1 ? "" : input_stream.GetImage();
					if (curChar == '\n' || curChar == '\r') {
						error_line++;
						error_column = 0;
					} else error_column++;
				}
				if (!EOFSeen) {
					input_stream.backup(1);
					error_after = curPos <= 1 ? "" : input_stream.GetImage();
				}
				throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
			}
		}
	}
	
	private void error(String msg) {
		anyErrors = true;
		System.err.println(msg + ": line " + input_stream.getEndLine() + ", column " + input_stream.getEndColumn());
	}

	private final int jjStopStringLiteralDfa_0(int pos, long active0) {
		switch (pos) {
			case 0:
				if ((active0 & 0x5000000010000L) != 0L) return 2;
				if ((active0 & 0xfffff8000000L) != 0L) {
					jjmatchedKind = 59;
					return 19;
				}
				return -1;
			case 1:
				if ((active0 & 0x4000000000000L) != 0L) return 0;
				if ((active0 & 0xffdff8000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 1;
					return 19;
				}
				if ((active0 & 0x2000000000L) != 0L) return 19;
                return -1;
			case 2:
				if ((active0 & 0x9fdff8000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 2;
					return 19;
				}
				if ((active0 & 0x600000000000L) != 0L) return 19;
                return -1;
			case 3:
				if ((active0 & 0x849f98000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 3;
					return 19;
				}
				if ((active0 & 0x1b4060000000L) != 0L) return 19;
                return -1;
			case 4:
				if ((active0 & 0x801e98000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 4;
					return 19;
				}
				if ((active0 & 0x48100000000L) != 0L) return 19;
                return -1;
			case 5:
				if ((active0 & 0x800a00000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 5;
					return 19;
				}
				if ((active0 & 0x1498000000L) != 0L) return 19;
                return -1;
			case 6:
				if ((active0 & 0x800000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 6;
					return 19;
				}
				if ((active0 & 0x800200000000L) != 0L) return 19;
                return -1;
			case 7:
				if ((active0 & 0x800000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 7;
					return 19;
				}
				return -1;
			case 8:
				if ((active0 & 0x800000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 8;
					return 19;
				}
				return -1;
			case 9:
				if ((active0 & 0x800000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 9;
					return 19;
				}
				return -1;
			case 10:
				if ((active0 & 0x800000000L) != 0L) {
					jjmatchedKind = 59;
					jjmatchedPos = 10;
					return 19;
				}
				return -1;
			default:
				return -1;
		}
	}


	private final int jjStartNfa_0(int pos, long active0) {
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}


	private int jjStopAtPos(int pos, int kind) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}


	private int jjMoveStringLiteralDfa0_0() {
		switch (curChar) {
			case 33:
				jjmatchedKind = 14;
				return jjMoveStringLiteralDfa1_0(0x400000L);
			case 38:
				return jjMoveStringLiteralDfa1_0(0x800000L);
			case 40:
				return jjStopAtPos(0, 6);
			case 41:
				return jjStopAtPos(0, 7);
			case 42:
				return jjStopAtPos(0, 15);
			case 43:
				return jjStopAtPos(0, 17);
			case 44:
				return jjStopAtPos(0, 25);
			case 45:
				return jjStopAtPos(0, 13);
			case 46:
				return jjStopAtPos(0, 12);
			case 47:
				jjmatchedKind = 16;
				return jjMoveStringLiteralDfa1_0(0x5000000000000L);
			case 59:
				return jjStopAtPos(0, 26);
			case 60:
				return jjStopAtPos(0, 18);
			case 61:
				jjmatchedKind = 20;
				return jjMoveStringLiteralDfa1_0(0x200000L);
			case 62:
				return jjStopAtPos(0, 19);
			case 83:
				return jjMoveStringLiteralDfa1_0(0x80000000L);
			case 84:
				return jjMoveStringLiteralDfa1_0(0x400000000L);
			case 88:
				return jjMoveStringLiteralDfa1_0(0x10000000000L);
			case 91:
				return jjStopAtPos(0, 8);
			case 93:
				return jjStopAtPos(0, 9);
			case 98:
				return jjMoveStringLiteralDfa1_0(0x800000000000L);
			case 99:
				return jjMoveStringLiteralDfa1_0(0x100000000L);
			case 101:
				return jjMoveStringLiteralDfa1_0(0x4200000000L);
			case 102:
				return jjMoveStringLiteralDfa1_0(0x40000000000L);
			case 105:
				return jjMoveStringLiteralDfa1_0(0x402000000000L);
			case 109:
				return jjMoveStringLiteralDfa1_0(0x40000000L);
			case 110:
				return jjMoveStringLiteralDfa1_0(0x300000000000L);
			case 112:
				return jjMoveStringLiteralDfa1_0(0x8000000L);
			case 114:
				return jjMoveStringLiteralDfa1_0(0x1000000000L);
			case 115:
				return jjMoveStringLiteralDfa1_0(0x810000000L);
			case 116:
				return jjMoveStringLiteralDfa1_0(0xa0000000000L);
			case 118:
				return jjMoveStringLiteralDfa1_0(0x20000000L);
			case 119:
				return jjMoveStringLiteralDfa1_0(0x8000000000L);
			case 123:
				return jjStopAtPos(0, 10);
			case 124:
				return jjMoveStringLiteralDfa1_0(0x1000000L);
			case 125:
				return jjStopAtPos(0, 11);
			default:
				return jjMoveNfa_0(3, 0);
		}
	}


	private int jjMoveStringLiteralDfa1_0(long active0) {
		try {
			curChar = input_stream.readChar();
		} catch(IOException e) {
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}


		switch (curChar) {
			case 38:
				if ((active0 & 0x800000L) != 0L) return jjStopAtPos(1, 23);
				break;
			case 42:
				if ((active0 & 0x4000000000000L) != 0L) return jjStartNfaWithStates_0(1, 50, 0);
				break;
			case 47:
				if ((active0 & 0x1000000000000L) != 0L) return jjStopAtPos(1, 48);
				break;
			case 61:
				if ((active0 & 0x200000L) != 0L) return jjStopAtPos(1, 21);
				else if ((active0 & 0x400000L) != 0L) return jjStopAtPos(1, 22);
				break;
			case 97:
				return jjMoveStringLiteralDfa2_0(active0, 0x40040000000L);
			case 101:
				return jjMoveStringLiteralDfa2_0(active0, 0x201000000000L);
			case 102:
				if ((active0 & 0x2000000000L) != 0L) return jjStartNfaWithStates_0(1, 37, 19);
				break;
			case 104:
				return jjMoveStringLiteralDfa2_0(active0, 0x88400000000L);
			case 105:
				return jjMoveStringLiteralDfa2_0(active0, 0x10000000000L);
			case 108:
				return jjMoveStringLiteralDfa2_0(active0, 0x4100000000L);
			case 110:
				return jjMoveStringLiteralDfa2_0(active0, 0x400000000000L);
			case 111:
				return jjMoveStringLiteralDfa2_0(active0, 0x800020000000L);
			case 114:
				return jjMoveStringLiteralDfa2_0(active0, 0x20000000000L);
			case 116:
				return jjMoveStringLiteralDfa2_0(active0, 0x90000000L);
			case 117:
				return jjMoveStringLiteralDfa2_0(active0, 0x100008000000L);
			case 120:
				return jjMoveStringLiteralDfa2_0(active0, 0x200000000L);
			case 121:
				return jjMoveStringLiteralDfa2_0(active0, 0x800000000L);
			case 124:
				if ((active0 & 0x1000000L) != 0L) return jjStopAtPos(1, 24);
				break;
			default:
				break;
		}
		return jjStartNfa_0(0, active0);
	}


	private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(0, old0);
		try {
			curChar = input_stream.readChar();
		} catch(IOException e) {
			jjStopStringLiteralDfa_0(1, active0);
			return 2;
		}


		switch (curChar) {
			case 97:
				return jjMoveStringLiteralDfa3_0(active0, 0x110000000L);
			case 98:
				return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
			case 105:
				return jjMoveStringLiteralDfa3_0(active0, 0x88060000000L);
			case 108:
				return jjMoveStringLiteralDfa3_0(active0, 0x140000000000L);
			case 110:
				return jjMoveStringLiteralDfa3_0(active0, 0x10800000000L);
			case 111:
				return jjMoveStringLiteralDfa3_0(active0, 0x800000000000L);
			case 114:
				return jjMoveStringLiteralDfa3_0(active0, 0x480000000L);
			case 115:
				return jjMoveStringLiteralDfa3_0(active0, 0x4000000000L);
			case 116:
				if ((active0 & 0x400000000000L) != 0L) return jjStartNfaWithStates_0(2, 46, 19);
                return jjMoveStringLiteralDfa3_0(active0, 0x1200000000L);
			case 117:
				return jjMoveStringLiteralDfa3_0(active0, 0x20000000000L);
			case 119:
				if ((active0 & 0x200000000000L) != 0L) return jjStartNfaWithStates_0(2, 45, 19);
				break;
			default:
				break;
		}
		return jjStartNfa_0(1, active0);
	}


	private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(1, old0);
		try {
			curChar = input_stream.readChar();
		} catch(IOException e) {
			jjStopStringLiteralDfa_0(2, active0);
			return 3;
		}


		switch (curChar) {
			case 99:
				return jjMoveStringLiteralDfa4_0(active0, 0x800000000L);
			case 100:
				if ((active0 & 0x20000000L) != 0L) return jjStartNfaWithStates_0(3, 29, 19);
				break;
			case 101:
				if ((active0 & 0x4000000000L) != 0L) return jjStartNfaWithStates_0(3, 38, 19);
				else if ((active0 & 0x20000000000L) != 0L) return jjStartNfaWithStates_0(3, 41, 19);
                return jjMoveStringLiteralDfa4_0(active0, 0x600000000L);
			case 105:
				return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
			case 108:
				if ((active0 & 0x100000000000L) != 0L) return jjStartNfaWithStates_0(3, 44, 19);
                return jjMoveStringLiteralDfa4_0(active0, 0x808008000000L);
			case 110:
				if ((active0 & 0x40000000L) != 0L) return jjStartNfaWithStates_0(3, 30, 19);
				break;
			case 115:
				if ((active0 & 0x80000000000L) != 0L) return jjStartNfaWithStates_0(3, 43, 19);
                return jjMoveStringLiteralDfa4_0(active0, 0x40100000000L);
			case 116:
				return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
			case 117:
				if ((active0 & 0x10000000000L) != 0L) return jjStartNfaWithStates_0(3, 40, 19);
                return jjMoveStringLiteralDfa4_0(active0, 0x1000000000L);
			default:
				break;
		}
		return jjStartNfa_0(2, active0);
	}


	private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(2, old0);
		try {
			curChar = input_stream.readChar();
		} catch(IOException e) {
			jjStopStringLiteralDfa_0(3, active0);
			return 4;
		}


		switch (curChar) {
			case 97:
				return jjMoveStringLiteralDfa5_0(active0, 0x400000000L);
			case 101:
				if ((active0 & 0x8000000000L) != 0L) return jjStartNfaWithStates_0(4, 39, 19);
				else if ((active0 & 0x40000000000L) != 0L) return jjStartNfaWithStates_0(4, 42, 19);
                return jjMoveStringLiteralDfa5_0(active0, 0x800000000000L);
			case 104:
				return jjMoveStringLiteralDfa5_0(active0, 0x800000000L);
			case 105:
				return jjMoveStringLiteralDfa5_0(active0, 0x18000000L);
			case 110:
				return jjMoveStringLiteralDfa5_0(active0, 0x280000000L);
			case 114:
				return jjMoveStringLiteralDfa5_0(active0, 0x1000000000L);
			case 115:
				if ((active0 & 0x100000000L) != 0L) return jjStartNfaWithStates_0(4, 32, 19);
				break;
			default:
				break;
		}
		return jjStartNfa_0(3, active0);
	}


	private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(3, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(4, active0);
			return 5;
		}


		switch (curChar) {
			case 97:
				return jjMoveStringLiteralDfa6_0(active0, 0x800000000000L);
			case 99:
				if ((active0 & 0x8000000L) != 0L) return jjStartNfaWithStates_0(5, 27, 19);
				else if ((active0 & 0x10000000L) != 0L) return jjStartNfaWithStates_0(5, 28, 19);
				break;
			case 100:
				if ((active0 & 0x400000000L) != 0L) return jjStartNfaWithStates_0(5, 34, 19);
				return jjMoveStringLiteralDfa6_0(active0, 0x200000000L);
			case 103:
				if ((active0 & 0x80000000L) != 0L) return jjStartNfaWithStates_0(5, 31, 19);
				break;
			case 110:
				if ((active0 & 0x1000000000L) != 0L) return jjStartNfaWithStates_0(5, 36, 19);
				break;
			case 114:
				return jjMoveStringLiteralDfa6_0(active0, 0x800000000L);
			default:
				break;
		}
		return jjStartNfa_0(4, active0);
	}


	private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(4, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(5, active0);
			return 6;
		}


		switch (curChar) {
			case 110:
				if ((active0 & 0x800000000000L) != 0L) return jjStartNfaWithStates_0(6, 47, 19);
				break;
			case 111:
				return jjMoveStringLiteralDfa7_0(active0, 0x800000000L);
			case 115:
				if ((active0 & 0x200000000L) != 0L) return jjStartNfaWithStates_0(6, 33, 19);
				break;
			default:
				break;
		}
		return jjStartNfa_0(5, active0);
	}


	private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(5, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(6, active0);
			return 7;
		}


		switch (curChar) {
			case 110:
				return jjMoveStringLiteralDfa8_0(active0, 0x800000000L);
			default:
				break;
		}
		return jjStartNfa_0(6, active0);
	}


	private int jjMoveStringLiteralDfa8_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(6, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(7, active0);
			return 8;
		}


		switch (curChar) {
			case 105:
				return jjMoveStringLiteralDfa9_0(active0, 0x800000000L);
			default:
				break;
		}
		return jjStartNfa_0(7, active0);
	}


	private int jjMoveStringLiteralDfa9_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(7, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(8, active0);
			return 9;
		}

		switch (curChar) {
			case 122:
				return jjMoveStringLiteralDfa10_0(active0, 0x800000000L);
			default:
				break;
		}
		return jjStartNfa_0(8, active0);
	}

	private int jjMoveStringLiteralDfa10_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(8, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(9, active0);
			return 10;
		}

		switch (curChar) {
			case 101:
				return jjMoveStringLiteralDfa11_0(active0, 0x800000000L);
			default:
				break;
		}
		return jjStartNfa_0(9, active0);
	}

	private int jjMoveStringLiteralDfa11_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(9, old0);
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			jjStopStringLiteralDfa_0(10, active0);
			return 11;
		}

		switch (curChar) {
			case 100:
				if ((active0 & 0x800000000L) != 0L) return jjStartNfaWithStates_0(11, 35, 19);
				break;
			default:
				break;
		}
		return jjStartNfa_0(10, active0);
	}

	private int jjStartNfaWithStates_0(int pos, int kind, int state) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) { return pos + 1; }
		return jjMoveNfa_0(state, pos + 1);
	}

	private int jjMoveNfa_0(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 22;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (;;) {
			if (++jjround == 0x7fffffff) ReInitRounds();
			if (curChar < 64) {
				long l = 1L << curChar;
				do {
					switch (jjstateSet[--i]) {
						case 3:
							if ((0x3fe000000000000L & l) != 0L) {
								if (kind > 55)
								kind = 55;
								jjCheckNAdd(5);
							} else if (curChar == 48) {
								if (kind > 55)
								kind = 55;
								jjCheckNAdd(21);
							} else if (curChar == 36) {
								if (kind > 59)
								kind = 59;
								jjCheckNAdd(19);
							} else if (curChar == 34) {
								jjCheckNAddStates(0, 2);
							} else if (curChar == 47) {
								jjstateSet[jjnewStateCnt++] = 2;
							}
							if (curChar == 48) jjstateSet[jjnewStateCnt++] = 6;
							break;
						case 0:
							if (curChar == 42) jjstateSet[jjnewStateCnt++] = 1;
							break;
						case 1:
							if ((0xffff7fffffffffffL & l) != 0L && kind > 49) kind = 49;
							break;
						case 2:
							if (curChar == 42) jjstateSet[jjnewStateCnt++] = 0;
							break;
						case 4:
							if ((0x3fe000000000000L & l) == 0L) break;
							if (kind > 55) kind = 55;
							jjCheckNAdd(5);
							break;
						case 5:
							if ((0x3ff000000000000L & l) == 0L) break;
							if (kind > 55) kind = 55;
							jjCheckNAdd(5);
							break;
						case 7:
							if ((0x3ff000000000000L & l) == 0L) break;
							if (kind > 57) kind = 57;
							jjstateSet[jjnewStateCnt++] = 7;
							break;
						case 8:
							if (curChar == 48) jjstateSet[jjnewStateCnt++] = 6;
							break;
						case 9:
							if (curChar == 34) jjCheckNAddStates(0, 2);
							break;
						case 10:
							if ((0xfffffffbffffdbffL & l) != 0L) jjCheckNAddStates(0, 2);
							break;
						case 12:
							if ((0x8400000000L & l) != 0L) jjCheckNAddStates(0, 2);
							break;
						case 13:
							if (curChar == 34 && kind > 58) kind = 58;
							break;
						case 14:
							if ((0xff000000000000L & l) != 0L) jjCheckNAddStates(3, 6);
							break;
						case 15:
							if ((0xff000000000000L & l) != 0L) jjCheckNAddStates(0, 2);
							break;
						case 16:
							if ((0xf000000000000L & l) != 0L) jjstateSet[jjnewStateCnt++] = 17;
							break;
						case 17:
							if ((0xff000000000000L & l) != 0L) jjCheckNAdd(15);
							break;
						case 18:
							if (curChar != 36) break;
							if (kind > 59) kind = 59;
							jjCheckNAdd(19);
							break;
						case 19:
							if ((0x3ff001000000000L & l) == 0L) break;
							if (kind > 59) kind = 59;
							jjCheckNAdd(19);
							break;
						case 20:
							if (curChar != 48) break;
							if (kind > 55) kind = 55;
							jjCheckNAdd(21);
							break;
						case 21:
							if ((0xff000000000000L & l) == 0L) break;
							if (kind > 56) kind = 56;
							jjCheckNAdd(21);
							break;
						default: break;
					}
				} while (i != startsAt);
			} else if (curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
						case 3:
						case 19:
							if ((0x7fffffe87fffffeL & l) == 0L) break;
							if (kind > 59) kind = 59;
							jjCheckNAdd(19);
							break;
						case 1:
							if (kind > 49) kind = 49;
							break;
						case 6:
							if (curChar == 120) jjCheckNAdd(7);
							break;
						case 7:
							if ((0x7e0000007eL & l) == 0L) break;
							if (kind > 57) kind = 57;
							jjCheckNAdd(7);
							break;
						case 10:
							if ((0xffffffffefffffffL & l) != 0L) jjCheckNAddStates(0, 2);
							break;
						case 11:
							if (curChar == 92) jjAddStates(7, 9);
							break;
						case 12:
							if ((0x14404410000000L & l) != 0L) jjCheckNAddStates(0, 2);
							break;
						default: break;
					}
				} while (i != startsAt);
			} else {
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
						case 1:
							if ((jjbitVec0[i2] & l2) != 0L && kind > 49) kind = 49;
							break;
						case 10:
							if ((jjbitVec0[i2] & l2) != 0L) jjAddStates(0, 2);
							break;
						default: break;
					}
				} while (i != startsAt);
			}
			if (kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 22 - (jjnewStateCnt = startsAt))) return curPos;
			try {
				curChar = input_stream.readChar();
        	} catch(IOException e) {
        		return curPos;
        	}
		}
	}

	private int jjMoveStringLiteralDfa0_3() {
		switch (curChar) {
			case 42:
				return jjMoveStringLiteralDfa1_3(0x20000000000000L);
			default:
				return 1;
		}
	}

	private int jjMoveStringLiteralDfa1_3(long active0) {
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			return 1;
		}

		switch (curChar) {
			case 47:
				if ((active0 & 0x20000000000000L) != 0L) return jjStopAtPos(1, 53);
				break;
			default:
				return 2;
		}
		return 2;
	}

	private int jjMoveStringLiteralDfa0_1() {
		return jjMoveNfa_1(0, 0);
	}

	private int jjMoveNfa_1(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 3;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (;;) {
			if (++jjround == 0x7fffffff)
				ReInitRounds();
			if (curChar < 64) {
				long l = 1L << curChar;
				do {
					switch (jjstateSet[--i]) {
						case 0:
							if ((0x2400L & l) != 0L) {
								if (kind > 51) kind = 51;
							}
							if (curChar == 13) jjstateSet[jjnewStateCnt++] = 1;
							break;
						case 1:
							if (curChar == 10 && kind > 51) kind = 51;
							break;
						case 2:
							if (curChar == 13) jjstateSet[jjnewStateCnt++] = 1;
							break;
						default: break;
					}
				} while (i != startsAt);
			} else if (curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
						default: break;
					}
				} while (i != startsAt);
			} else {
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
						default: break;
					}
				} while (i != startsAt);
			}
			if (kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt))) return curPos;
			try {
	            curChar = input_stream.readChar();
	        } catch(IOException e) {
	        	return curPos;
	        }
		}
	}

	private int jjMoveStringLiteralDfa0_2() {
		switch (curChar) {
			case 42:
				return jjMoveStringLiteralDfa1_2(0x10000000000000L);
			default:
				return 1;
		}
	}

	private int jjMoveStringLiteralDfa1_2(long active0) {
		try {
            curChar = input_stream.readChar();
        } catch(IOException e) {
			return 1;
		}

		switch (curChar) {
			case 47:
				if ((active0 & 0x10000000000000L) != 0L) return jjStopAtPos(1, 52);
				break;
			default:
				return 2;
		}
		return 2;
	}

	private void ReInitRounds() {
		int i;
		jjround = 0x80000001;
		for (i = 22; i-- > 0;) jjrounds[i] = 0x80000000;
	}

	private Token jjFillToken() {
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		String im = jjstrLiteralImages[jjmatchedKind];
		curTokenImage = (im == null) ? input_stream.GetImage() : im;
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = new Token(jjmatchedKind, curTokenImage);

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}

	private void SkipLexicalActions(Token matchedToken) {
		switch (jjmatchedKind) {
			case 62:
				image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
				error("Illegal token");
				break;
			default:
				break;
		}
	}

	private void MoreLexicalActions() {
		jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
		switch (jjmatchedKind) {
			case 49:
				image.append(input_stream.GetSuffix(jjimageLen));
				jjimageLen = 0;
				input_stream.backup(1);
				break;
			default:
				break;
		}
	}

	private void jjCheckNAdd(int state) {
		if (jjrounds[state] != jjround) {
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}

	private void jjAddStates(int start, int end) {
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		} while (start++ != end);
	}

	private void jjCheckNAddStates(int start, int end) {
		do {
			jjCheckNAdd(jjnextStates[start]);
		} while (start++ != end);
	}
}
