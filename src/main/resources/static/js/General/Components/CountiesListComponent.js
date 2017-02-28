/**
 * Created by nevyt on 2/21/2017.
 */
var CountiesListComponent = React.createClass({

  render: function(){
    var countyInfo =this.props.countyList.map(function(county,index) {
      return(
          <tr>
              <td>{county.id}</td>
              <td><a href={'#/county/'+ county.id}>{county.name}</a></td>
          </tr>
      );
    });
    return (
      <div>
        <div className="container">
          <h1 className='yellow'>Apygardų sąrašas</h1>
        </div>
        <div id="exTab1" className="container">
          <div className="tab-content clearfix">
            <table width="100%" className="table table-striped table-hover" id="dataTables-example">
              <thead>
              <tr>
                <th> Nr. </th>
                <th> Apygarda </th>
              </tr>
              </thead>
              <tbody>
                {countyInfo}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
});
window.CountiesListComponent = CountiesListComponent;
