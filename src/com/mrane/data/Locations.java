package com.mrane.data;

import java.util.HashMap;

import android.content.Context;

public class Locations {
	public HashMap<String, Marker> data = new HashMap<String, Marker>();

	public Locations(Context context) {
		Marker m0 = new Room(
				"ACRE Advanced Centre for Research in Electronics", "ACRE",
				4081, 1344, 1, "CRNTS NanoTech. & Science Research Centre",
				"Inside", "");
		data.put(m0.name, m0);
		Marker m1 = new Marker(
				"Aerospace Engineering Department",
				"Aero engg",
				4153,
				2249,
				1,
				"http://www.aero.iitb.ac.in/\nPhone No.: (+91-22) 2576 7101 / (+91-22) 2576 7102\nFax No.: (+91-22) 2572 2602.\nEmail: office@aero.iitb.ac.in \nThe department offers academic programs for B. Tech, M. Tech, Ph. D. Specialization are offered: Aerodynamics, Control and Guidance, Propulsion, Structures, and Systems Engineering");
		data.put(m1.name, m1);
		Marker m2 = new Marker("Ananta", "0", 4695, 925, 3, "");
		data.put(m2.name, m2);
		Marker m3 = new Marker("Aravali", "0", 4404, 968, 3, "");
		data.put(m3.name, m3);
		Marker m4 = new Room("ATM - Canara Bank (near Gulmohar)",
				"Canara ATM Gulmohar", 2989, 2154, 6, "Canara Bank",
				"First floor", "");
		data.put(m4.name, m4);
		Marker m5 = new Room("ATM - Canara Bank H6", "Canara ATM H6", 2500,
				748, 6, "Hostel 06", "Near", "");
		data.put(m5.name, m5);
		Marker m6 = new Marker("ATM - State Bank", "SBI ATM", 3028, 989, 6, "");
		data.put(m6.name, m6);
		Marker m7 = new Marker("B-19", "0", 1413, 2885, 3, "");
		data.put(m7.name, m7);
		Marker m8 = new Marker("Badminton Court", "0", 3042, 1244, 8, "");
		data.put(m8.name, m8);
		Marker m9 = new Marker(
				"Bio-diesel Lab",
				"0",
				4220,
				1558,
				1,
				"\nwww.che.iitb.ac.in/chea/biosynth/be-a-part.php\ncontact@biosynthiitb.org\nProject ‘Biosynth’ is an initiative by the students of the Department of Chemical Engineering, IIT Bombay to install a Biodiesel plant. This student-managed initiative was started in 2008. The R & D activities at the plant include:\nthe adaptation of the standard biodiesel production process to the available waste vegetable oil, the quality control for the biodiesel produced, allied issues pertaining to design and planning and research projects.");
		data.put(m9.name, m9);
		Marker m11 = new Marker("Boat House", "0", 1960, 1757, 9,
				"Timings: 6 am to 6 pm");
		data.put(m11.name, m11);
		Marker m12 = new Room("Brewberrys Cafe", "Brewberrys", 2967, 1271, 5,
				"Hostel 07", "Inside",
				"Ph no: 022 65641001, Hostel 8, IIT Bombay, Powai, Mumbai");
		data.put(m12.name, m12);
		Marker m13 = new Marker(
				"Campus School",
				"0",
				3331,
				2865,
				7,
				"Principal: MS.BHAGWAT A.S\n022 2576 8992\nCampus School began as a primary school on 29 June 1976. V to X standard was added by 1986 with 100% results in X std in 1986, 1987 and 1988. The junior college classes FYJC or XI Std and SYJC or XII Std were started in science stream under Principal Chandra Rao in 1989. The Primary to JC complex has about 400 students on its roll, 35 teachers and 20 non-teaching staff. Admission to all classes is restricted to children of IIT employees with some seats made available to the children of NITIE and SAMEER employees.");
		data.put(m13.name, m13);
		Marker m14 = new Marker(
				"Central Library",
				"0",
				3820,
				1455,
				1,
				"http://www.library.iitb.ac.in/\n022 2576 8921\nLibrary Working Hours\nMonday through Friday 0900 – 2300 hrs\nMonday through Friday 0900 – 0100 hrs (during examination)\nSaturdays / Sundays / Holidays   1000 – 1700 hrs\nSaturdays / Sundays / Holidays   1000 – 0100 hrs (during examination)\n\nCirculation Hours\nMonday through Friday 0900–2000 hrs\nSaturday, Sunday & Holidays 1100–1300 hrs\nSelf Issue / self Check out, any time till library is open.\nStudy room books issued for overnight, one hour before closing of the library on all days");
		data.put(m14.name, m14);
		Marker m15 = new Marker(
				"CESE Centre for Environmental Science and Engineering",
				"CESE",
				4340,
				1985,
				1,
				"http://www.cese.iitb.ac.in/\nTel +91-22-25767851 Fax +91-22-25764650\nThe Centre offers M.Tech. and Ph.D. programmes, which are interdisciplinary in nature and consists of course work followed by a research project. The duration of the Ph.D. programme varies depending upon the background of the candidate.");
		data.put(m15.name, m15);
		Marker m16 = new Marker(
				"Chemistry Department",
				"Chemistry",
				3788,
				2350,
				1,
				"http://www.chem.iitb.ac.in/\nPhone: 022-2576-7151 \nFax: 022-2572-3480, 022-2576-7152 \nThe department offers academic programs for M.Sc, Ph.D, (M.Sc + Ph.D) Dual Degree Courses, 4 year BS degree course (From 2014, the 5 year Integrated M.Sc course has been replaced by the 4 year B.S course).");
		data.put(m16.name, m16);
		Marker m17 = new Marker(
				"Civil Engineering Department",
				"Civil",
				3879,
				1804,
				1,
				"http://www.civil.iitb.ac.in/\nTel:+91-22-2576 7301\nFax:+91-22-2576 7302\nThe department offers academic programs for B Tech, Dual Degree (B Tech + M Tech), M Tech, MS and Ph.D across different divisions such as\nBuilding Technology and Construction Management (BTCM)\nEnvironmental and Water Resources Engineering (EWRE)\nGeotechnical Engineering (GT)\nStructural Engineering (ST)\nTransportation Engineering (TR)");
		data.put(m17.name, m17);
		Marker m18 = new Marker("Convocation Hall", "0", 3255, 1711, 4,
				"022 2576  2781");
		data.put(m18.name, m18);
		Marker m19 = new Building(
				"CRNTS NanoTech. & Science Research Centre",
				"CRNTS",
				4081,
				1344,
				1,
				new String[] {
						"ACRE Advanced Centre for Research in Electronics",
						"SAIF Sophisticated Analytical Instruments Facility" },
				"http://www.iitb.ac.in/~crnts/\nTel:+91-22-25767691\nCRNTS offers programs such as\nDual degree programme-1 (B.Tech. Engg. Physics & M.Tech. Engg. Physics with specialization in Nanoscience. Admission Through: JEE. Offered by: Department of Physics. Duration: 5 years)\n\nDual degree programme-2 (M.Sc. Physics & M.Tech. in Materials Science with specialization in Nanoscience., Input : B.Sc. (Physics), Admission through: J.A.M. Jointly offered by Department of Physics & Department of Metallurgical Engg. & Materials Science)\n\nPh.D ( Interdisciplinary program. Research Domains include Nanomaterials, Nanobiotechnology,  Nanofluidics, Nanoelectronics, Nanomanufacturing, Nanosensors, Computational research in Nanosystems)");
		data.put(m19.name, m19);
		Marker m20 = new Marker(
				"CSRE Centre of Studies in Resources Engineering",
				"CSRE",
				4096,
				1993,
				1,
				"http://www.csre.iitb.ac.in/\nTel:+91 22 2576 7662\nThe centre offers Ph.D, M Tech, UG Minor and institute elective. Research areas include Spatial Analysis, Digital Image Processing, Global Positioning System (GPS) and Photogrammetry, Geocomputational Systems, Microwave Remote Sensing, Snow and Glacier Studies, Geology and Mineral Resources, Agro-Informatics and rural development, Terrain evaluation group, Environment, natural hazards and disaster management, Coastal and marine sciences");
		data.put(m20.name, m20);
		Marker m21 = new Marker("Director’s Bungalow", "0", 2593, 2408, 3, "");
		data.put(m21.name, m21);
		Marker m22 = new Marker("DRDO", "0", 4125, 1048, 3, "");
		data.put(m22.name, m22);
		Marker m23 = new Marker(
				"Earth Science Department",
				"Earth Sci",
				4038,
				2123,
				1,
				"http://www.geos.iitb.ac.in/\n+91-22-2576 7251, 2576 7265\nThe department offers academic programs for M.Sc (Applied Geology, Applied Geophysics, Geoexploration), M Tech (Petroleum Geoscience) and Ph.D. Research areas include Mineralogy, Geochemistry and Ore Deposits Structural Geology Igneous and Metamorphic Petrology Engineering geology, Hydro-geology Sedimentology Stratigraphy and Micro-Paleontology Mathematical Geology/ Ore Deposit Modelling Rock Magnetism and Marine Geology Seismology, Geothermics");
		data.put(m23.name, m23);
		Marker m24 = new Marker("Electrical Engineering Annex Building",
				"Elec engg Anx", 3657, 1934, 1,
				"http://www.ee.iitb.ac.in/\n+91-22-2576 4408 \n\n");
		data.put(m24.name, m24);
		Marker m25 = new Marker(
				"Electrical Engineering Department",
				"Elec engg",
				3932,
				1997,
				1,
				"http://www.ee.iitb.ac.in/\n +91-22-2576-7401/ 7042\nB Tech, B Tech Honors, Dual Degree Program (B Tech + M Tech), M Tech (Full time 2 yrs, Part time 3yrs with specializations such as Communications, Engineering (termed as EE1), Control and Computing (EE2), Power Electronics and Power System (EE3), Microelectronics and VLSI (EE4), Electronic Systems (EE5)) and Ph D");
		data.put(m25.name, m25);
		Marker m27 = new Marker("Electrical Maintenence", "Elec Maint", 4544,
				1830, 1, "022 2576 7971/4077");
		data.put(m27.name, m27);
		Marker m28 = new Marker("Guest House/ Jalvihar", "Jalvihar", 2610,
				2138, 3, "022 2576 8940/8942/8943");
		data.put(m28.name, m28);
		Marker m29 = new Marker("Guest House/ Vanvihar", "Vanvihar", 2881,
				2106, 3, "022 2576 1200/8945");
		data.put(m29.name, m29);
		Marker m30 = new Room("Gulmohar Restaurant", "Gulmohar", 2989, 2154, 5,
				"Canara Bank", "Second floor", "022 2576 2783/2786");
		data.put(m30.name, m30);
		Marker m31 = new Marker("Hospital", "0", 3018, 1917, 9,
				"022 2576 7051, Ambulance 022 2576 1101");
		data.put(m31.name, m31);
		Marker m32 = new Marker(
				"Hostel 01",
				"H1",
				3908,
				1077,
				2,
				"Hostel security: 022-2576 2601\nHall Manager: 022-2576 2701\nG.Sec: Ratikant 9930836852");
		data.put(m32.name, m32);
		Marker m33 = new Marker(
				"Hostel 10 Girls Annex",
				"H10 Annx",
				2886,
				2452,
				2,
				"Hostel security: 022-2576 2619\nHall Manager: 022-2576\nG.Sec: Sagarika Kumar 9167273231");
		data.put(m33.name, m33);
		Marker m34 = new Marker(
				"Hostel 10 Girls Hostel New",
				"H10 New",
				3005,
				2342,
				2,
				"Hostel security: 022-2576 2610\nHall Manager: 022-2576 2710\nG.Sec: Madhu Lekha 9769372532");
		data.put(m34.name, m34);
		Marker m35 = new Marker("Hostel 10A Girls QIP building (P. Staff)",
				"H10 QIP", 4716, 1448, 2,
				"Hostel security: 022-2576\nHall Manager: 022-2576\nG.Sec:");
		data.put(m35.name, m35);
		Marker m36 = new Building(
				"Hostel 11 Girls Hostel",
				"H11",
				2987,
				1368,
				2,
				new String[] { "Printing and photocopying H11" },
				"Hostel security: 022-2576 2611\nHall Manager: 022-2576 2711\nG.Sec: Nanditha 9769834234");
		data.put(m36.name, m36);
		Marker m37 = new Marker(
				"Hostel 12",
				"H12",
				2096,
				667,
				2,
				"Hostel security: 022-2576 2612\nHall Manager: 022-2576 2712\nG.Sec: Ashutosh 9167782489");
		data.put(m37.name, m37);
		Marker m38 = new Marker(
				"Hostel 13",
				"H13",
				1918,
				745,
				2,
				"Hostel security: 022-2576 2613\nHall Manager: 022-2576 2713\nG.Sec: Raj Kumar Yadav 9769484219");
		data.put(m38.name, m38);
		Marker m39 = new Marker(
				"Hostel 14",
				"H14",
				2114,
				800,
				2,
				"Hostel security: 022-2576 2614\nHall Manager: 022-2576 2714\nG.Sec: Mayuresh Pant 9730694513");
		data.put(m39.name, m39);
		Marker m40 = new Marker("Hostel 15", "H15", 4196, 870, 2,
				"Hostel security: 022-2576 2715\nHall Manager: 022-2576\nG.Sec:");
		data.put(m40.name, m40);
		Marker m41 = new Marker("Hostel 16", "H16", 3972, 849, 2,
				"Hostel security: 022-2576 2716\nHall Manager: 022-2576\nG.Sec:");
		data.put(m41.name, m41);
		Marker m42 = new Marker(
				"Hostel 02",
				"H2",
				3672,
				1000,
				2,
				"Hostel security: 022-2576 2602\nHall Manager: 022-2576 2702\nG.Sec: Manohar Reddy Devarpalli 8796879949");
		data.put(m42.name, m42);
		Marker m43 = new Marker(
				"Hostel 03",
				"H3 ",
				3435,
				946,
				2,
				"Hostel security: 022-2576 2603\nHall Manager: 022-2576 2703\nG.Sec: Arvind Jangid 9820525369");
		data.put(m43.name, m43);
		Marker m44 = new Marker(
				"Hostel 04",
				"H4",
				3176,
				867,
				2,
				"Hostel security: 022-2576 2604\nHall Manager: 022-2576 2704\nG.Sec: Kumar Gaurav 9969800320");
		data.put(m44.name, m44);
		Marker m45 = new Marker(
				"Hostel 05",
				"H5",
				2820,
				970,
				2,
				"Hostel security: 022-2576 2605\nHall Manager: 022-2576 2705\nG.Sec: Shashank Patidar 9820717487");
		data.put(m45.name, m45);
		Marker m46 = new Building(
				"Hostel 06",
				"H6",
				2500,
				748,
				2,
				new String[] { "ATM - Canara Bank H6" },
				"Hostel security: 022-2576 2606\nHall Manager: 022-2576 2706\nG.Sec: Anil Reddy 9022623186");
		data.put(m46.name, m46);
		Marker m47 = new Building(
				"Hostel 07",
				"H7",
				2454,
				942,
				2,
				new String[] { "Brewberrys Cafe" },
				"Hostel security: 022-2576 2607\nHall Manager: 022-2576 2707\nG.Sec: Archit Laddha 9930239739");
		data.put(m47.name, m47);
		Marker m48 = new Building(
				"Hostel 08",
				"H8",
				2834,
				1257,
				2,
				new String[] { "Printing and photocopying H8" },
				"Hostel security: 022-2576 2608\nHall Manager: 022-2576 2708\nG.Sec: Mayur Kalambe 9920147585");
		data.put(m48.name, m48);
		Marker m49 = new Marker(
				"Hostel 09",
				"H9",
				2676,
				844,
				2,
				"Hostel security: 022-2576 2609\nHall Manager: 022-2576 2709\nG.Sec: Shubham Meena 9619835583");
		data.put(m49.name, m49);
		Marker m50 = new Marker(
				"HSS Humanities and Social Sciences",
				"HSS",
				3911,
				2171,
				1,
				"http://www.hss.iitb.ac.in/\n022 2576 7351/ 022 2576 73517352\nThe department offers academic programs for B. Tech, M. Phil, Ph. D. Research areas include Economics, English, Philosophy, Psychology, Sociology");
		data.put(m50.name, m50);
		Marker m51 = new Marker(
				"IDC Industrial Design Centre",
				"IDC",
				4207,
				1732,
				1,
				"http://www.idc.iitb.ac.in\n091-022-2576 7801\nThe department offers academic programs for M. Des and Ph. D. Sub-disciplines include Product Design, Industrial Design, Visual Communication, Animation, Interaction Design, Mobility and vehicle design");
		data.put(m51.name, m51);
		Marker m52 = new Marker("Indoor Stadium", "0", 3480, 1081, 8, "");
		data.put(m52.name, m52);
		Marker m53 = new Room(
				"IRCC Indian Research & Consultancy Centre",
				"IRCC",
				3505,
				1867,
				1,
				"SOM School of Management",
				"Level 2",
				"91-22-2576 7030, 91-22-2576 7039\nwww.ircc.iitb.ac.in\nThe Industrial Research and Consultancy Centre (IRCC) co-ordinates and facilitates all research and development activities at the Institute.");
		data.put(m53.name, m53);
		Marker m54 = new Marker(
				"KReSIT Kanwal Rekhi School of Information Technology ",
				"KResit",
				3301,
				2106,
				1,
				"For admission / general queries 2576 7967/77\nOffice 022 2576 7901 / 7902\nSecurity 022 2576 2784");
		data.put(m54.name, m54);
		Marker m55 = new Marker("Kshitij Udyan", "0", 2971, 1744, 9, "");
		data.put(m55.name, m55);
		Marker m56 = new Marker("KV Kendriya Vidyalaya ", "0", 3883, 2714, 7,
				"022 2576 8983");
		data.put(m56.name, m56);
		Marker m57 = new Marker("Lake Side Gate", "0", 792, 2777, 10,
				"022 2576 1124");
		data.put(m57.name, m57);
		Marker m58 = new Marker("Lecture Hall Complex-1", "Lec Hall 1", 3570,
				2152, 2, "");
		data.put(m58.name, m58);
		Marker m59 = new Marker("Lecture Hall Complex-2", "Lec Hall 2", 3779,
				2220, 2, "");
		data.put(m59.name, m59);
		Marker m60 = new Building("Main Building", "0", 3628, 1640, 1,
				new String[] { "HCU Hostel Coordinating Unit",
						"GATE Graduate apptitude test in engineering Office",
						"JAM Joint Admission Test for M.Sc. Office",
						"JEE Joint Entrance Examination Office",
						"Printing and photocopying Main Building" }, "");
		data.put(m60.name, m60);
		Marker m61 = new Building("Main Gate", "0", 2259, 3237, 10,
				new String[] { "SBI State Bank of India, IIT Powai branch" },
				"02576 8978/1123. Tum Tum coupons available at Main Gate");
		data.put(m61.name, m61);
		Marker m62 = new Marker("Market Gate, Y point Gate", "0", 3829, 2972,
				10, "2576 8979/1121");
		data.put(m62.name, m62);
		Marker m63 = new Marker(
				"Mathematics Department",
				"Math",
				3928,
				1496,
				1,
				"http://www.math.iitb.ac.in/\n022-2576 7451\nB Tech (core courses), MSc (ASI, Maths), Ph D");
		data.put(m63.name, m63);
		Marker m64 = new Marker(
				"Mechanical Engineering",
				"Mech ",
				4009,
				1645,
				1,
				"http://www.me.iitb.ac.in/\n(91) 22 - 2572 2545 Ext. 7500, 7501, 2576 7500, 2576 7501.\nThe department offers academic programs for B. Tech, Dual degree, M. Tech and Ph. D. Research areas include design enggineering, manufacturing enggineering, thermal and fluid enggineering");
		data.put(m64.name, m64);
		Marker m65 = new Marker("Medical Store", "0", 4000, 2808, 9, "");
		data.put(m65.name, m65);
		Marker m66 = new Marker(
				"MEMS Metallurgical Engineering and Material Science",
				"MEMS",
				3631,
				2044,
				1,
				"http://www.met.iitb.ac.in/\n022 2576 7601/7602\nThe department offers academic programs for B. Tech, Dual degree (B Tech + M Tech), M. Tech and Ph. D. Dual degree specializations include Ceramics and Composites and Metallurgical Process Engineering. M Tech specializations include Materials Science, Steel Technology, Process Engineering and Corrosion Science.");
		data.put(m66.name, m66);
		Marker m67 = new Marker("NASA Non- Academic Staff Association", "NASA",
				4326, 1896, 1, "022 2576 8919");
		data.put(m67.name, m67);
		Marker m68 = new Marker("NCC Office", "0", 3387, 1421, 9,
				"022 2576 8917");
		data.put(m68.name, m68);
		Marker m69 = new Marker("Nestle Cafe (Coffee Shack)", "0", 3492, 1784,
				5, "");
		data.put(m69.name, m69);
		Marker m70 = new Marker(
				"New Computer Science Engineering Department",
				"New CSE",
				3422,
				1986,
				1,
				"http://www.cse.iitb.ac.in/\n+91-22-2576 7901/02\nThe department offers academic programs for B. Tech, Dual degree (B Tech + M Tech), M Tech, Dual Degree PG (M. Tech + Ph. D) and Ph. D. Research areas include algorithms, programming languages and Compilers, database and information systems, artificial intelligence and natural language processing, software engineering, formal methods, distributed systems, computer networks, data mining, computer graphics, computer vision and image understanding, real-time and embedded systems, formal languages and bio-inspired computing");
		data.put(m70.name, m70);
		Marker m71 = new Marker("Nilgiri B 24", "B 24", 3318, 2324, 3, "");
		data.put(m71.name, m71);
		Marker m72 = new Marker(
				"Old Computer Science Engineering Department",
				"Old CSE",
				4002,
				1545,
				1,
				"http://www.cse.iitb.ac.in/\n022 2576 7701/7702/2771\nThe department offers academic programs for B. Tech, Dual degree (B Tech + M Tech), Dual Degree PG (M. Tech + Ph. D) and Ph. D. Research areas include algorithms, programming languages and Compilers, database and information systems, artificial intelligence and natural language processing, software engineering, formal methods, distributed systems, computer networks, data mining, computer graphics, computer vision and image understanding, real-time and embedded systems, formal languages and bio-inspired computing");
		data.put(m72.name, m72);
		Marker m73 = new Marker("Old Multistoried Building- Residence", "0",
				1413, 2885, 3, "Building lift phone 022 2576 2884");
		data.put(m73.name, m73);
		Marker m74 = new Marker(
				"ONGC Research Centre",
				"0",
				4234,
				2162,
				1,
				"Currently, the centre is working on a project titled Physical and Numerical Models for Un-conventional Flood Patterns. The project relates to increasing countrys currrent oil production by means of Enhanced Oil Recovery (EOR) processes. ");
		data.put(m74.name, m74);
		Marker m75 = new Marker(
				"OrthoCad Lab",
				"0",
				4644,
				1767,
				1,
				"The OrthoCAD Network Research Cell was established in 2007 to jump-start indigenous research and development activities in orthopaedic reconstruction systems. The OrthoCAD Network addresses a critical need for mega-prostheses to reconstruct massive gaps or loss of bone from osteo-sarcoma (cancer), congenital (birth) defects or trauma (accidents).");
		data.put(m75.name, m75);
		Marker m76 = new Marker("Padmavati Devi Temple", "0", 946, 2178, 9, "");
		data.put(m76.name, m76);
		Marker m77 = new Marker("PC Saxena Auditorium (LT)",
				"PC Saxena Audi, LT", 3648, 1790, 4, "022 2576 4999");
		data.put(m77.name, m77);
		Marker m78 = new Marker(
				"Physics Department",
				"0",
				3479,
				2247,
				1,
				"The department offers academic programs for B.Tech Engineering Physics (through JEE), Dual degree B.Tech + M.Tech in Engineering Physics with specialisation in Nanoscience (through JEE), MSc Physics 2 Years programme (through JAM), Dual degree programme of MSc and PhD in physics (through JAM). Research areas include Condensed Matter Physics, Photonics and Optics, Nuclear Physics, High Energy Physics, Statistical Physics");
		data.put(m78.name, m78);
		Marker m79 = new Marker("Post Office", "0", 3918, 2846, 9,
				"022 2576 2774");
		data.put(m79.name, m79);
		Marker m80 = new Marker("Power House", "0", 4671, 1590, 9, "");
		data.put(m80.name, m80);
		Marker m81 = new Marker("Printing Press", "0", 4326, 1896, 9,
				"022 2576 8961");
		data.put(m81.name, m81);
		Marker m82 = new Marker("SAC Students Activity Centre", "SAC", 3599,
				1225, 8, "022 2576 8968");
		data.put(m82.name, m82);
		Marker m83 = new Room(
				"SAIF Sophisticated Analytical Instruments Facility", "SAIF",
				4081, 1344, 1, "CRNTS NanoTech. & Science Research Centre",
				"Inside", "022-25767691/2");
		data.put(m83.name, m83);
		Marker m84 = new Marker("Seminar Hall", "0", 3792, 2025, 4,
				"022 2576 4912, for booking 022 2576 4420");
		data.put(m84.name, m84);
		Marker m85 = new Marker("Shishu Vihar", "0", 2949, 1495, 9, "");
		data.put(m85.name, m85);
		Marker m86 = new Marker("Shivalik C 23", "C 23", 3129, 2612, 3, "");
		data.put(m86.name, m86);
		Marker m87 = new Building(
				"SOM School of Management",
				"SOM",
				3505,
				1867,
				1,
				new String[] { "IRCC Indian Research & Consultancy Centre" },
				"http://www.som.iitb.ac.in/\nOffice - +91 22 2576 7781\nPh. D. admissions - +91 22 2576 7782\nM. Mgt. admissions - +91 22 2576 8781 \nThe department offers academic programs in Master Of Management Programme - Full Time, Doctoral Programme in Management - PhD Degree, Management Development Programme - For Corporate Executives");
		data.put(m87.name, m87);
		Marker m88 = new Marker("Staff Canteen", "Staff Cant.", 3622, 1547, 5,
				"022 2576 8952");
		data.put(m88.name, m88);
		Marker m89 = new Marker("Staff Club", "0", 2931, 2027, 8,
				"022 3576 4075");
		data.put(m89.name, m89);
		Marker m90 = new Marker("Staff Hostel", "0", 2872, 1472, 3,
				"022 2576 1113");
		data.put(m90.name, m90);
		Marker m91 = new Room("SBI State Bank of India, IIT Powai branch",
				"SBI", 2259, 3237, 6, "Main Gate", "Near",
				"Phone (022) 25722894-25721103\nFax\nwork hours: 10:30 am to 4:30 pm");
		data.put(m91.name, m91);
		Marker m92 = new Marker("Swimming Pool (new)", "S. Pool new", 3558,
				1141, 8, "022 2576 2755");
		data.put(m92.name, m92);
		Marker m93 = new Marker("Swimming Pool (old)", "S. Pool old", 3742,
				1229, 8, "022 2576 8967");
		data.put(m93.name, m93);
		Marker m94 = new Marker("Tansa House (Proj. Staff Boys)", "Tansa",
				3028, 932, 3, "022 2576 2620");
		data.put(m94.name, m94);
		Marker m95 = new Marker("Tennis Court (new)", "0", 3046, 1513, 8, "");
		data.put(m95.name, m95);
		Marker m96 = new Marker("Tennis Court (old)", "0", 3177, 1624, 8, "");
		data.put(m96.name, m96);
		Marker m97 = new Marker("Victor Menezes Convention Centre", "VMCC",
				4110, 1847, 4, "022 2576 1125");
		data.put(m97.name, m97);
		Marker m98 = new Marker("Vidya Niwas", "0", 4911, 1111, 3, "");
		data.put(m98.name, m98);
		Marker m99 = new Marker("Vihar House", "0", 4558, 1429, 3, "");
		data.put(m99.name, m99);
		Marker m100 = new Marker("White House", "0", 1909, 2799, 3,
				"A wing 022 2576 2885,\nB wing 022 2576 2840");
		data.put(m100.name, m100);
		Marker m101 = new Building("Canara Bank", "0", 2989, 2154, 6,
				new String[] { "ATM - Canara Bank (near Gulmohar)",
						"Gulmohar Restaurant" }, "02576 2797");
		data.put(m101.name, m101);
		Marker m113 = new Marker("Centrifugal Lab", "0", 4370, 1395, 1, "");
		data.put(m113.name, m113);
		Marker m114 = new Marker(
				"Concrete Technology Lab",
				"Concrete Tech Lab",
				4330,
				1495,
				1,
				"Comes under Structural Engineering Laboratories of Civil Engineering  Department ");
		data.put(m114.name, m114);
		Marker m115 = new Marker("Cummins Engine Research facility", "Cummins",
				4350, 1580, 1,
				"Incharge: Prof. Anuradda Ganesh\n022 2576 4506\nLocation");
		data.put(m115.name, m115);
		Marker m116 = new Marker(
				"ENELEK Power Sine Lab",
				"0",
				4160,
				1620,
				9,
				"Enelek is a technology driven company, dedicated to delivery to quality and affordability through state-of-the art solar thermal & solar power products, systems and providing technology solutions & services.\nAddress: CM-06, SINE Office\n3rd Floor, CSRE Building\nIIT Bombay\nPowai, Mumbai - 400076\nContact No. - +91 91679 39941 | info@enelek.com\nCareers Enquiry - career@enelek.com");
		data.put(m116.name, m116);
		Marker m117 = new Marker(
				"Energy Systems Lab",
				"0",
				4230,
				1435,
				1,
				"Comes under Energy Systems Engineering Department\nhttp://www.ese.iitb.ac.in/\n+91-22-2576-7890\nThe department offers academic programs in Dual degree (B Tech + M Tech), M Tech, M.Sc.-Ph.D and Ph.D. Research areas include Energy Efficiency and Conservation, Solar PV and Thermal, Battery and Storage Engineering, Hydrogen and Fuel Cells, Smart microgrids, Biomass and Bio-Fuels, Wind Energy, Nuclear");
		data.put(m117.name, m117);
		Marker m118 = new Marker(
				"Fluid Mechanics and Fluid Power Lab",
				"Fluid Mech and Fluid Power Lab",
				4060,
				1585,
				1,
				"Comes under Mechanical Engineering Department\nFluid Mechanics Lab 022 2576 4232 \nFluid Power Lab 022 2576 4532/4549");
		data.put(m118.name, m118);
		Marker m119 = new Marker(
				"Fuel Cell Research Facility",
				"0",
				4525,
				1680,
				1,
				"Comes under Energy Systems Engineering Department\nhttp://www.ese.iitb.ac.in/\n+91-22-2576 4897");
		data.put(m119.name, m119);
		Marker m120 = new Marker("Geotechnical Engg. Lab", "0", 4280, 1425, 1,
				"Comes under Civil Engineering Department\n+91-22-2576 4320");
		data.put(m120.name, m120);
		Marker m121 = new Marker(
				"GMFL Lab / Geophysical and multiphase Flows Lab", "0", 4530,
				1485, 1, "");
		data.put(m121.name, m121);
		Marker m122 = new Marker("Greenhouse Lab", "0", 4790, 1055, 1, "");
		data.put(m122.name, m122);
		Marker m123 = new Marker("Heat Pump Lab", "0", 4153, 1539, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4527/4593");
		data.put(m123.name, m123);
		Marker m124 = new Marker("Heat Transfer and Thermodynamic Lab", "0",
				4390, 1550, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4533");
		data.put(m124.name, m124);
		Marker m125 = new Marker(
				"Heavy Structure Lab",
				"0",
				4330,
				1495,
				1,
				"Comes under Structural Engineering Laboratories of Civil Engineering Department 022 2576 4323");
		data.put(m125.name, m125);
		Marker m126 = new Marker(
				"Hydraulics Lab",
				"0",
				4215,
				1490,
				1,
				"Comes under Civil Engineering Department\nWater Resources Hydraulics Lab 022 2576 4303");
		data.put(m126.name, m126);
		Marker m127 = new Marker("Hydraulics Lab (New)", "0", 4110, 1525, 1,
				"Comes under Civil Engineering Department\nHYDRAULICS LAB (VMCC) 022 2576 4301");
		data.put(m127.name, m127);
		Marker m128 = new Marker("Hydraulics Lab Workshop", "0", 4180, 1450, 1,
				"");
		data.put(m128.name, m128);
		Marker m129 = new Marker("IC Engine and Combustion Lab", "0", 4295,
				1570, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4586");
		data.put(m129.name, m129);
		Marker m130 = new Marker(
				"E-Yantra Lab (CSE Dept.)",
				"0",
				4790,
				1055,
				1,
				"ERTS Lab\nFirst Floor, KReSIT Building, CSE Department\nIIT Bombay - Powai, Mumbai 400076\nhelpdesk@e-yantra.org");
		data.put(m130.name, m130);
		Marker m131 = new Marker("Machine Lab", "0", 1445, 1669, 1,
				"Comes under Electrical Engineering Department\n022 2576 4422");
		data.put(m131.name, m131);
		Marker m132 = new Marker("Machine Tool Lab", "0", 4410, 1740, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4518/4537");
		data.put(m132.name, m132);
		Marker m133 = new Marker("Metal Forming Lab", "0", 4290, 1470, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4561");
		data.put(m133.name, m133);
		Marker m134 = new Marker("Micro Fluidics Lab", "0", 1620, 1783, 1, "");
		data.put(m134.name, m134);
		Marker m135 = new Marker("N1 Bay", "0", 4525, 1680, 1,
				"Comes under Chemical Engineering Department\n022 2576 4211");
		data.put(m135.name, m135);
		Marker m136 = new Marker("N2 Bay", "0", 1445, 1669, 1, "");
		data.put(m136.name, m136);
		Marker m137 = new Marker("N3 Bay", "0", 4360, 1645, 1, "");
		data.put(m137.name, m137);
		Marker m138 = new Marker("National Geotechnical Centrifuge Facility",
				"0", 4370, 1395, 1, "");
		data.put(m138.name, m138);
		Marker m139 = new Marker("OrthoCad Lab", "0", 4683, 1702, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4399");
		data.put(m139.name, m139);
		Marker m140 = new Marker("Old ONGC Lab", "0", 4275, 1555, 1, "");
		data.put(m140.name, m140);
		Marker m141 = new Marker("Physics Lab (Ist Years)", "0", 4585, 1540, 1,
				"Comes under Physics department");
		data.put(m141.name, m141);
		Marker m142 = new Marker("Refrigeration, A/C and Cryogenics Lab", "0",
				4225, 1630, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4587");
		data.put(m142.name, m142);
		Marker m143 = new Marker("RM Lab (Rapid manufacturing)", "0", 4650,
				1757, 1, "");
		data.put(m143.name, m143);
		Marker m144 = new Marker("Rock Cutting Lab", "0", 3921, 2095, 1, "");
		data.put(m144.name, m144);
		Marker m145 = new Marker("Rock Powdering Lab", "0", 3921, 2095, 1, "");
		data.put(m145.name, m145);
		Marker m146 = new Marker("S1 Bay", "0", 4490, 1760, 1, "");
		data.put(m146.name, m146);
		Marker m147 = new Marker("S2 Bay", "0", 4410, 1740, 1, "022 2576 4213");
		data.put(m147.name, m147);
		Marker m148 = new Marker("S3 Bay", "0", 4325, 1720, 1, "");
		data.put(m148.name, m148);
		Marker m149 = new Marker(
				"SEMT Lab /Structural Evaluation & Material Technologies Lab",
				"0", 4280, 1510, 1,
				"Comes under Civil Engineering Department\n022 2576 4318");
		data.put(m149.name, m149);
		Marker m150 = new Marker(
				"SITAC/ Structural integrity Testing and Analysis Centre", "0",
				4355, 1750, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4528");
		data.put(m150.name, m150);
		Marker m151 = new Marker(
				"Solar Lab",
				"0",
				4230,
				1435,
				1,
				"Comes under Chemistry Department\n022 2576 4887\nPhone: 022 2576 4173 for SOLAR LAB (308)");
		data.put(m151.name, m151);
		Marker m152 = new Marker("Steam Power Lab", "0", 4376, 1526, 1,
				"Comes under Mechanical Engineering Department\n022 2576 4584");
		data.put(m152.name, m152);
		Marker m153 = new Marker(
				"SMAμL Suman Mashruwala Advanced Microengineering Lab",
				"SMAμL",
				4431,
				1615,
				1,
				"Phone:-+(91)(22)2576 7519 ; 4534\nFax:-+(91)(22)2572-6875\nE-mail: gandhi@me.iitb.ac.in");
		data.put(m153.name, m153);
		Marker m154 = new Marker(
				"Supercritical fluid Processing facility (Chemical Engg.)",
				"0", 4490, 1760, 1,
				"Comes under Chemical Engineering Department\n022 2576 7201/7202");
		data.put(m154.name, m154);
		Marker m155 = new Marker(
				"Thermal Hydraulic Test Facility",
				"0",
				1463,
				1536,
				1,
				"Comes under Mechanical Engineering Department\n(91) 22 - 2572 2545 Ext. 7500, 7501, 2576 7500, 2576 7501.");
		data.put(m155.name, m155);
		Marker m156 = new Marker("Tinkerers Lab (STAB)", "0", 4225, 1630, 1, "");
		data.put(m156.name, m156);
		Marker m157 = new Marker(
				"Treelabs",
				"0",
				4225,
				1630,
				9,
				"TreeLabs is an inventions factory to nurture and encourage inventors and innovations, leading to entrepreneurship\nAddress: Electrical Engineering Dept, \nN-5 Bay (near Mech Engg Dept), \nI.I.T.Bombay, Powai, \nMumbai - 400076, India. \nPhone: +91 22 2572-3001 / 2576-4410\nEmail: Prof. Dipankar : dipankar@iitbombay.org");
		data.put(m157.name, m157);
		Marker m158 = new Marker("UG Lab/S2 Bay", "0", 4410, 1740, 1,
				"Comes under Chemical Engineering Department \n022 2576 4207");
		data.put(m158.name, m158);
		Marker m159 = new Marker("UG Lab (Ist Years)", "0", 4585, 1540, 1, "");
		data.put(m159.name, m159);
		Marker m160 = new Room("Printing and photocopying H11", "Printer",
				2987, 1368, 10, "Hostel 11 Girls Hostel", "Inside", "");
		data.put(m160.name, m160);
		Marker m161 = new Room("Printing and photocopying H8", "Printer", 2834,
				1257, 10, "Hostel 08", "Inside", "");
		data.put(m161.name, m161);
		Marker m162 = new Room("Printing and photocopying Main Building",
				"Printer", 3628, 1640, 10, "Main Building", "Inside", "");
		data.put(m162.name, m162);

	}
}