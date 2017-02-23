/**
 * Created by nevyt on 2/21/2017.
 */
var CountiesListComponent = React.createClass({

    render: function(){
        var countyInfo =this.props.countyList.map(function(county,index) {
            return(
                <tr>
                    <td>{county.id}</td>
                    <td><a href={"#/counties/"+ county.id}>{county.name}</a></td>
                </tr>
            );
        });
        return (
            <div className="panel panel-default">
                <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
                    <h4 style={{display:'inline'}}><i className="fa fa-table"></i>&nbsp; Rinkimų apygardų sąrašas </h4>
                </div>
                    <div className="panel-body">
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
        );
    }
});
window.CountiesListComponent = CountiesListComponent;