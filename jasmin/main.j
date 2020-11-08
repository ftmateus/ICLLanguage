.source main.j
.class public main
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V

	.limit locals 2
	.limit stack 256

	getstatic java/lang/System/out Ljava/io/PrintStream;

	new frame_0					; Frame 0 started
	dup
	invokespecial frame_0/<init>()V
	dup
	astore_1
	dup
	new frame_1					; Frame 1 started
	dup
	invokespecial frame_1/<init>()V
	dup
	aload_1
	putfield frame_1/SL Lframe_0;
	dup
	astore_1
	dup
	new frame_2					; Frame 2 started
	dup
	invokespecial frame_2/<init>()V
	dup
	aload_1
	putfield frame_2/SL Lframe_1;
	dup
	astore_1
	dup
	sipush 2
	putfield frame_2/z I
	pop						; Frame 2 finished
	aload_1
	checkcast frame_2
	getfield frame_2/z I
	sipush 1
	iadd
	aload_1					; Next 3ll: Get parent frame [1] and store it into local var 1
	checkcast frame_2				; Current frame stored in 1: 2
	getfield frame_2/SL Lframe_1;
	astore_1
	putfield frame_1/y I
	pop						; Frame 1 finished
	aload_1
	checkcast frame_1
	getfield frame_1/y I
	sipush 2
	iadd
	aload_1					; Next 3ll: Get parent frame [0] and store it into local var 1
	checkcast frame_1				; Current frame stored in 1: 1
	getfield frame_1/SL Lframe_0;
	astore_1
	putfield frame_0/x I
	pop						; Frame 0 finished
	new frame_3					; Frame 3 started
	dup
	invokespecial frame_3/<init>()V
	dup
	aload_1
	putfield frame_3/SL Lframe_0;
	dup
	astore_1
	dup
	sipush 1
	putfield frame_3/y I
	pop						; Frame 3 finished
	new frame_4					; Frame 4 started
	dup
	invokespecial frame_4/<init>()V
	dup
	aload_1
	putfield frame_4/SL Lframe_3;
	dup
	astore_1
	dup
	new frame_5					; Frame 5 started
	dup
	invokespecial frame_5/<init>()V
	dup
	aload_1
	putfield frame_5/SL Lframe_4;
	dup
	astore_1
	dup
	sipush 1
	putfield frame_5/w I
	pop						; Frame 5 finished
	aload_1
	checkcast frame_5
	getfield frame_5/w I
	aload_1					; Next 3ll: Get parent frame [4] and store it into local var 1
	checkcast frame_5				; Current frame stored in 1: 5
	getfield frame_5/SL Lframe_4;
	astore_1
	putfield frame_4/z I
	pop						; Frame 4 finished
	aload_1
	checkcast frame_4
	getfield frame_4/SL Lframe_3;
	getfield frame_3/SL Lframe_0;
	getfield frame_0/x I
	aload_1
	checkcast frame_4
	getfield frame_4/SL Lframe_3;
	getfield frame_3/y I
	iadd
	aload_1
	checkcast frame_4
	getfield frame_4/z I
	iadd
	aload_1					; Next 3ll: Get parent frame [3] and store it into local var 1
	checkcast frame_4				; Current frame stored in 1: 4
	getfield frame_4/SL Lframe_3;
	astore_1
	aload_1					; Next 3ll: Get parent frame [0] and store it into local var 1
	checkcast frame_3				; Current frame stored in 1: 3
	getfield frame_3/SL Lframe_0;
	astore_1

	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return

.end method