/**
 * Created by nevyt on 2/21/2017.
 */
function getCounties(self){
    axios
        .get('/county')
        .then(function(response){
            self.setState({
                countyList: response.data,
                isLoading: false,
            });
        })

}
var CountiesContainer = React.createClass({
    getInitialState(){
        return {
            countyList: [],
            isLoading : true,
        };
    },
    componentWillMount(){
        getCounties(this);
    },
    render: function() {
      document.title='Apygardos Rinkimai 2017';
        if (this.state.isLoading) {
            return (
                <div>
                    <img src='./Images/loading.gif'/>
                </div>
            );
        } else {
            return (
                <CountiesListComponent
                    countyList={this.state.countyList}
                />
            );
        }
    }
});
window.CountiesContainer = CountiesContainer;
