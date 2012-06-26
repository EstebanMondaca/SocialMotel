package net.sgoliver.android;

//Importacion de las librerias
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

//Creacion de la clase
public class AndroidMapas extends MapActivity {

	private MapView mapa = null;
	private Button btnSatelite = null;
	private Button btnCentrar = null;
	private Button btnAnimar = null;
	private Button btnMover = null;
	private MapController controlMapa = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Referencia a los controles
        mapa = (MapView)findViewById(R.id.mapa);
        btnSatelite = (Button)findViewById(R.id.BtnSatelite);
        btnCentrar = (Button)findViewById(R.id.BtnCentrar);
        btnAnimar = (Button)findViewById(R.id.BtnAnimar);
        btnMover = (Button)findViewById(R.id.BtnMover);
        
        //Controlador del mapa
        controlMapa = mapa.getController();
        
        //Controles de zoom sobre el mapa
        mapa.setBuiltInZoomControls(true);
        
		//Capa de marcas
		List<Overlay> capas = mapa.getOverlays();
		OverlayMapa om = new OverlayMapa();
		capas.add(om);
		mapa.postInvalidate();
        
        //Boton que permite encender o apagar la vista de satelite
		btnSatelite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mapa.isSatellite())
					mapa.setSatellite(false);
				else
					mapa.setSatellite(true);
			}
		});
        
        //Boton que permite centrar el mapa en la marca de posicion inicial 
		btnCentrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Double latitud = -33.42*1E6;
				Double longitud = -70.64*1E6;
				
				GeoPoint loc = 
					new GeoPoint(latitud.intValue(), longitud.intValue());
				
				controlMapa.setCenter(loc);
				controlMapa.setZoom(10);
			}
		});
        
        //Boton que anima el desplazamiento hacia el marcador
		btnAnimar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Double latitud = -33.42*1E6;
				Double longitud = -70.64*1E6;
				
				GeoPoint loc = 
					new GeoPoint(latitud.intValue(), longitud.intValue());
				
				controlMapa.animateTo(loc);
				
				int zoomActual = mapa.getZoomLevel();
				for(int i=zoomActual; i<10; i++)
				{
					controlMapa.zoomIn();
				}
			}
		});
        
        //Boton que desplaza el mapa hacia la derecha en 40 pixeles
		btnMover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				controlMapa.scrollBy(40, 40);
			}
		});
    }
    
    @Override
    protected boolean isRouteDisplayed() {
    	return true;
    }
}